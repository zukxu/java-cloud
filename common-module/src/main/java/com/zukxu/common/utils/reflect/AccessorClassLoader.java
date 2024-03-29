package com.zukxu.common.utils.reflect;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.security.ProtectionDomain;
import java.util.HashSet;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * <p>
 * 访问器/操作器的类加载器
 * </p>
 *
 * @author xupu
 * @since 2022/8/26 16:22:07
 */

class AccessorClassLoader extends ClassLoader {

    /**
     * Weak-references to class loaders, to avoid perm gen memory leaks,
     * for example in app servers/web containters
     * if the reflectasm library (including this class) is loaded outside the deployed applications (WAR/EAR)
     * using ReflectASM/Kryo (exts, user classpath, etc).
     * The key is the parent class loader and the value is the AccessorClassLoader,
     * both are weak-referenced in the hash table.
     */
    private static final WeakHashMap<ClassLoader, WeakReference<AccessorClassLoader>> ACCESS_CLASS_LOADERS = new WeakHashMap<>();

    /**
     * Fast-path for classes loaded in the same ClassLoader as this class.
     */
    private static final ClassLoader SELF_CONTEXT_PARENT_CLASS_LOADER = getParentClassLoader(AccessorClassLoader.class);

    private static volatile AccessorClassLoader selfContextAccessorClassLoader = new AccessorClassLoader(SELF_CONTEXT_PARENT_CLASS_LOADER);

    private static volatile Method defineClassMethod;

    private final Set<String> localClassNames = new HashSet<>();

    private AccessorClassLoader(ClassLoader parent) {
        super(parent);
    }

    static AccessorClassLoader get(Class<?> type) {
        ClassLoader parent = getParentClassLoader(type);
        // 1. fast-path:
        if (SELF_CONTEXT_PARENT_CLASS_LOADER.equals(parent)) {
            if (selfContextAccessorClassLoader == null) {
                // DCL with volatile semantics
                synchronized (ACCESS_CLASS_LOADERS) {
                    if (selfContextAccessorClassLoader == null) {
                        selfContextAccessorClassLoader = new AccessorClassLoader(SELF_CONTEXT_PARENT_CLASS_LOADER);
                    }
                }
            }
            return selfContextAccessorClassLoader;
        }
        // 2. normal search:
        synchronized (ACCESS_CLASS_LOADERS) {
            WeakReference<AccessorClassLoader> ref = ACCESS_CLASS_LOADERS.get(parent);
            if (ref != null) {
                AccessorClassLoader accessorClassLoader = ref.get();
                if (accessorClassLoader != null) {
                    return accessorClassLoader;
                } else {
                    // the value has been GC-reclaimed, but still not the key (defensive sanity)
                    ACCESS_CLASS_LOADERS.remove(parent);
                }
            }
            AccessorClassLoader accessorClassLoader = new AccessorClassLoader(parent);
            ACCESS_CLASS_LOADERS.put(parent, new WeakReference<>(accessorClassLoader));
            return accessorClassLoader;
        }
    }

    private static ClassLoader getParentClassLoader(Class<?> type) {
        ClassLoader parent = type.getClassLoader();
        if (parent == null) {
            parent = ClassLoader.getSystemClassLoader();
        }
        return parent;
    }

    private static Method getDefineClassMethod() throws Exception {
        // DCL on volatile
        if (defineClassMethod == null) {
            synchronized (ACCESS_CLASS_LOADERS) {
                defineClassMethod = ClassLoader.class.getDeclaredMethod("defineClass", String.class, byte[].class, int.class, int.class, ProtectionDomain.class);
                try {
                    defineClassMethod.setAccessible(true);
                } catch (Exception ignored) {
                }
            }
        }
        return defineClassMethod;
    }

    /**
     * Returns null if the accessor class has not yet been defined.
     */
    Class<?> loadAccessorClass(String name) {
        // No need to check the parent class loader if the accessor class hasn't been defined yet.
        if (localClassNames.contains(name)) {
            try {
                return loadClass(name, false);
            } catch (ClassNotFoundException ex) {
                // Should not happen, since we know the class has been defined.
                throw new RuntimeException(ex);
            }
        }
        return null;
    }

    Class<?> defineAccessorClass(String name, byte[] bytes) throws ClassFormatError {
        localClassNames.add(name);
        return defineClass(name, bytes);
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        // These classes come from the classloader that loaded AccessorClassLoader.
        if (name.equals(MethodAccessor.class.getName())) {
            return MethodAccessor.class;
        }
        // All other classes come from the classloader that loaded the type we are accessing.
        return super.loadClass(name, resolve);
    }

    private Class<?> defineClass(String name, byte[] bytes) throws ClassFormatError {
        ProtectionDomain protectionDomain = getClass().getProtectionDomain();
        try {
            // Attempt to load the accessor class in the same loader,
            // which makes protected and default accessor members accessible.
            return (Class<?>) getDefineClassMethod().invoke(super.getParent(), name, bytes, 0, bytes.length, protectionDomain);
        } catch (Exception ignored) {
            // continue with the definition in the current loader
            // (won't have access to protected and package-protected members)
        }
        return super.defineClass(name, bytes, 0, bytes.length, protectionDomain);
    }

}
