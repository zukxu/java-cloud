<template>
  <div id="build">
    <v-form-designer id="formDesigner" ref="vfDesigner" :designer-config="designerConfig"
                     :banned-widgets="bannedWidgets">
      <!-- 自定义按钮插槽演示 -->
      <template #customToolButtons>
        <el-button type="text" @click="handleFormAdd" v-if="isAdd"><i class="el-icon-finished"/>保存</el-button>
        <el-button type="text" @click="handleFormUpd" v-else><i class="el-icon-finished"/>更新</el-button>
        <el-button type="text" @click="backTo"><i class="el-icon-back"/>返回</el-button>
      </template>
    </v-form-designer>
    <!--表单配置详情-->
    <el-dialog :title="formTitle" :visible.sync="formOpen" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="表单名称" prop="formName">
          <el-input v-model="form.formName" placeholder="请输入表单名称"/>
        </el-form-item>
        <el-form-item label="表单Key" prop="formKey">
          <el-input v-model="form.formKey" placeholder="请输入表单Key"/>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="saveFormJson" v-if="isAdd">新 增</el-button>
        <el-button type="primary" @click="updFormJson" v-else>更 新</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {addForm, getFormDetails, updateForm} from "@/api/flowable/form"

export default {
  name: "build",
  data() {
    return {
      fieldListApi: {},
      bannedWidgets: [],
      designerConfig: {
        //是否显示语言切换菜单
        languageMenu: false,
        //是否显示GitHub、文档等外部链接
        externalLink: false,
        //是否显示表单模板
        formTemplates: false,
        //是否显示导出代码按钮
        exportCodeButton: false,
        //是否显示生成SFC按钮
        generateSFCButton: false,
      },
      formOpen: false,
      isAdd: true,
      formTitle: "",
      // 表单参数
      form: {
        formId: null,
        formName: null,
        formKey: null,
        formContent: null,
        remark: null,
      },
      rules: {}
    }
  },
  created() {
  },
  mounted() {
    const formId = this.$route.query && this.$route.query.formId;
    //编辑状态
    if (formId) {
      getFormDetails(formId).then((res) => {
        let formJson = JSON.parse(res.data.formContent);
        this.$refs.vfDesigner.setFormJson(formJson)
        this.form = res.data;
        this.isAdd = false
      });
    } else {
      //为空就清空上一次的残留
      this.$refs.vfDesigner.clearDesigner()
    }

  },
  methods: {
    /** 表单新增 */
    handleFormAdd() {
      this.getFormJson()
      this.formTitle = "添加表单";
    },
    /**
     * 表单更新
     */
    handleFormUpd() {
      this.getFormJson()
      this.formTitle = "更新表单";
    },
    getFormJson() {
      let formJson = this.$refs.vfDesigner.getFormJson()
      this.form.formContent = JSON.stringify(formJson);
      this.formOpen = true;
    },
    backTo() {
      console.log("返回")
      // 关闭当前标签页并返回上个页面
      this.$router.go(-1)
    },
    // 表单重置
    reset() {
      this.form = {
        formId: null,
        formName: null,
        formKey: null,
        formContent: null,
        remark: null,
      };
    },
    //保存数据
    saveFormJson() {
      console.log(this.form)
      addForm(this.form).then((res) => {
        console.log(res)
        this.$message.success('表单已保存')
        // 关闭当前标签页并返回上个页面
        this.backTo()
      })
    },
    updFormJson() {
      console.log(this.form)
      updateForm(this.form).then((res) => {
        console.log(res)
        this.$message.success('表单已更新')
        // 关闭当前标签页并返回上个页面
        this.backTo()
      })
    },
    // 取消按钮
    cancel() {
      this.formOpen = false;
      this.reset();
    },
  }
}
</script>

<style scoped lang="scss">
body {
  margin: 0; /* 如果页面出现垂直滚动条，则加入此行CSS以消除之 */
}

#build {
  #formDesigner {
    margin-left: 0%
  }
}
</style>
