<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="部署名称" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入部署名称" clearable style="width: 240px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-upload2" size="mini" @click="handleUpload" v-hasPermi="['flowable:deployment:upload']">上传部署</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete" v-hasPermi="['flowable:deployment:remove']">删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="deploymentList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="50" align="center" />
      <el-table-column label="部署ID" align="center" prop="id" v-if="columns.id.visible" />
      <el-table-column label="部署名称" align="center" prop="name" v-if="columns.name.visible" :show-overflow-tooltip="true" />
      <el-table-column label="分类" align="center" prop="category" v-if="columns.category.visible" />
      <el-table-column label="部署时间" align="center" prop="deployTime" v-if="columns.deployTime.visible" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.deployTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="120" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" v-hasPermi="['flowable:deployment:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <el-dialog :title="'上传流程部署'" :visible.sync="uploadOpen" width="400px" append-to-body>
      <el-form ref="uploadForm" :model="uploadForm" :rules="uploadRules" label-width="80px">
        <el-form-item label="部署名称" prop="name">
          <el-input v-model="uploadForm.name" placeholder="请输入部署名称" />
        </el-form-item>
        <el-form-item label="流程文件">
          <el-upload
            class="upload-demo"
            action=""
            :auto-upload="false"
            :file-list="fileList"
            :accept=".bpmn,.zip"
            :on-change="handleFileChange"
          >
            <el-button size="small" type="primary">点击选择文件</el-button>
            <div slot="tip" class="el-upload__tip">只能上传.bpmn或.zip文件</div>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitUpload">确定</el-button>
        <el-button @click="uploadOpen = false">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getDeploymentList, deleteDeployment } from '@/api/flowable/deployment'

export default {
  name: 'Deployment',
  data() {
    return {
      loading: true,
      showSearch: true,
      deploymentList: [],
      total: 0,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: ''
      },
      uploadOpen: false,
      uploadForm: {
        name: ''
      },
      fileList: [],
      uploadRules: {
        name: [
          { required: true, message: '部署名称不能为空', trigger: 'blur' }
        ]
      },
      columns: {
        id: { visible: true },
        name: { visible: true },
        category: { visible: true },
        deployTime: { visible: true }
      },
      single: true,
      multiple: true
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      getDeploymentList().then(response => {
        this.deploymentList = response.data || []
        this.total = response.total || 0
        this.loading = false
      })
    },
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    resetQuery() {
      this.queryParams = {
        pageNum: 1,
        pageSize: 10,
        name: ''
      }
      this.getList()
    },
    handleSelectionChange(val) {
      this.multiple = val.length > 0
      this.single = val.length === 1
    },
    handleDelete(row) {
      const deploymentId = row.id
      this.$confirm('确定删除选中的部署吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteDeployment(deploymentId).then(response => {
          if (response.code === 200) {
            this.$message.success('删除成功')
            this.getList()
          } else {
            this.$message.error(response.msg)
          }
        })
      })
    },
    handleUpload() {
      this.uploadOpen = true
      this.uploadForm = { name: '' }
      this.fileList = []
    },
    handleFileChange(file, fileList) {
      this.fileList = fileList
    },
    submitUpload() {
      if (!this.fileList.length) {
        this.$message.error('请选择要上传的文件')
        return
      }
      this.$message.success('上传功能开发中')
      this.uploadOpen = false
    }
  }
}
</script>