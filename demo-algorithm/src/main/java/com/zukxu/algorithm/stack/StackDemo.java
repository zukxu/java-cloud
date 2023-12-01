package com.zukxu.algorithm.stack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * 栈demo
 *
 * @author zukxu
 * CreateTime: 2021/5/7 0007 22:35
 */
public class StackDemo {

    public static void main(String[] args) {
        StackDemo stackDemo = new StackDemo();
        //stackDemo.reverseArrayByStack();
        //stackDemo.stackForQueue();
        //stackDemo.minStack();
        /*int[] pushArray = new int[]{1, 2, 3, 4, 5};
        List<List<Integer>> validSequences = new ArrayList<>();
        stackDemo.backtrack(pushArray, new ArrayList<>(), new boolean[pushArray.length], validSequences);
        System.out.println("有效的出栈序列数量为：" + validSequences.size());
        System.out.println("有效的出栈序列为：");
        for (List<Integer> sequence : validSequences) {
            System.out.println(sequence);
        }*/
        /*
        int[] popArray1 = new int[]{5, 4, 3, 2, 1};
        int[] popArray2 = new int[]{4, 3, 5, 2, 1};
        System.out.println(stackDemo.isPopOrder(pushArray, popArray1));
        System.out.println(stackDemo.isPopOrder(pushArray, popArray2));*/


    }

    /**
     * 1、栈实现队列
     * 用两个栈来实现一个队列，完成队列的 Push 和 Pop 操作。
     * <p>
     * 队列的特性是先进先出
     * </p>
     */
    public void stackForQueue() {
        int[] myArray = {23, 93, 56, 92, 39};
        Stack<Integer> in = new Stack<>();
        Stack<Integer> out = new Stack<>();
        //数组入到in栈
        System.out.println("模拟队列入栈操作：");
        for (int node : myArray) {
            System.out.print(node + " ");
            push(in, node);
        }
        //数组反转入到out栈中
        try {
            pop(in, out);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("\n模拟队列出栈操作");
        //按照顺序对out栈进行pop操作
        for (int i = 0; i < myArray.length; i++) {
            System.out.print(out.pop() + " ");
        }
    }

    private void push(Stack<Integer> in, int node) {
        in.push(node);
    }

    private void pop(Stack<Integer> in, Stack<Integer> out) throws Exception {
        if (out.isEmpty())
            while (!in.isEmpty())
                out.push(in.pop());

        if (out.isEmpty())
            throw new Exception("queue is empty");

    }

    /**
     * 2、最小栈minStack
     * 实现一个包含 min() 函数的栈，该方法返回当前栈中最小的值。
     * <p>
     * 使用一个额外的 minStack，栈顶元素为当前栈中最小的值。在对栈进行 push 入栈和 pop 出栈操作时，同样需要对 minStack 进行入栈出栈操作，从而使 minStack 栈顶元素一直为当前栈中最小的值。
     * 在进行 push 操作时，需要比较入栈元素和当前栈中最小值，将值较小的元素 push 到 minStack 中。
     * </p>
     */
    public void minStack() {
        int[] myArray = {23, 93, 56, 12, 39};
        Stack<Integer> dataStack = new Stack<>();
        Stack<Integer> minStack = new Stack<>();
        for (int node : myArray) {
            dataStack.push(node);
            minStack.push(minStack.isEmpty() ? node : Math.min(minStack.peek(), node));//保证minStack的顶帧一直是最小的值 peek()函数：返回最顶部的元素并且不pop
        }
        System.out.println("当前栈中最top的元素为：" + dataStack.peek());
        System.out.println("当前栈中最min的元素为：" + minStack.peek());

        System.out.println("栈中的数字顺序：");
        for (int i = 0; i < myArray.length; i++) {
            System.out.print(dataStack.pop() + " ");
        }
        System.out.println("\n最小栈中的数字顺序：");
        for (int i = 0; i < myArray.length; i++) {
            System.out.print(minStack.pop() + " ");
        }

    }

    /**
     * 3、栈的压入，弹出顺序判断
     * <p>
     * 输入两个整数序列，第一个序列表示栈的压入顺序，请判断第二个序列是否为该栈的弹出顺序。假设压入栈的所有数字均不相等。
     * 例如序列 1,2,3,4,5 是某栈的压入顺序，序列 4,5,3,2,1 是该压栈序列对应的一个弹出序列，但 4,3,5,1,2 就不可能是该压栈序列的弹出序列。
     * </p>
     *
     * @param pushSequence 压栈顺序
     * @param popSequence  出栈顺序
     * @return boolean
     */
    public boolean isPopOrder(int[] pushSequence, int[] popSequence) {
        if (pushSequence == null || popSequence == null || pushSequence.length != popSequence.length) {
            return false;
        }
         /*标准答案：
    使用一个栈来模拟压入弹出操作。
    每次入栈一个元素后，都要判断一下栈顶元素是不是当前出栈序列 popSequence 的第一个元素，
    如果是的话则执行出栈操作并将 popSequence 往后移一位，继续进行判断。
    在代码中，我们使用了一个辅助栈  stack  来模拟栈的压入和弹出操作。
    我们使用两个指针  pushIndex  和  popIndex  分别表示压入序列和弹出序列的索引。
    我们遍历压入序列，每次将元素压入栈中，并检查栈顶元素是否与弹出序列的当前元素相等。
    如果相等，则将栈顶元素弹出，并将弹出序列的索引后移。
    最后，我们检查栈是否为空，如果为空，则说明第二个序列是给定栈的弹出
    * */
        int n = pushSequence.length;
        Stack<Integer> stack = new Stack<>();
        for (int pushIndex = 0, popIndex = 0; pushIndex < n; pushIndex++) {
            stack.push(pushSequence[pushIndex]);
            while (popIndex < n && !stack.isEmpty() && stack.peek() == popSequence[popIndex]) {
                stack.pop();
                popIndex++;
            }
        }
        return stack.isEmpty();
    }

    /**
     * 判断有效出栈顺序
     */
    public void backtrack(int[] pushSequence, List<Integer> popped, boolean[] used, List<List<Integer>> validSequences) {
        if (popped.size() == pushSequence.length) {
            validSequences.add(new ArrayList<>(popped));
            return;
        }

        for (int i = 0; i < pushSequence.length; i++) {
            if (!used[i]) {
                used[i] = true;
                popped.add(pushSequence[i]);
                backtrack(pushSequence, popped, used, validSequences);
                popped.remove(popped.size() - 1);
                used[i] = false;
            }
        }
    }

    /** 使用Stack反转数组 */
    public void reverseArrayByStack() {
        int[] myArray = {23, 93, 56, 92, 39};
        Stack<Integer> stack = new Stack<>();
        // 将数组元素推入栈中
        for (int j : myArray) {
            stack.push(j);
        }
        // 创建一个新的数组，长度与原始数组相同
        int[] reverseArray = new int[myArray.length];
        int size = stack.size();
        // 从栈中弹出元素，并将其放入新数组中
        for (int i = 0; i < myArray.length; i++) {
            reverseArray[i] = stack.pop();
        }

        System.out.println("Reversed array is :" + Arrays.toString(reverseArray));

    }

}
