<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="流程名称" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入流程名称" clearable style="width: 240px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="状态" prop="suspended">
        <el-select v-model="queryParams.suspended" placeholder="流程状态" clearable style="width: 240px">
          <el-option :label="'全部'" :value="" />
          <el-option :label="'激活'" :value="false" />
          <el-option :label="'挂起'" :value="true" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-play" size="mini" :disabled="single" @click="handleStart" v-hasPermi="['flowable:process:start']">启动流程</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-pause" size="mini" :disabled="single" @click="handleSuspend" v-hasPermi="['flowable:process:suspend']">挂起流程</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-play" size="mini" :disabled="single" @click="handleActivate" v-hasPermi="['flowable:process:activate']">激活流程</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="processList" @selection-change="handleSelectionChange" @row-click="handleRowClick">
      <el-table-column type="selection" width="50" align="center" />
      <el-table-column label="流程ID" align="center" prop="id" v-if="columns.id.visible" />
      <el-table-column label="流程名称" align="center" prop="name" v-if="columns.name.visible" :show-overflow-tooltip="true" />
      <el-table-column label="流程标识" align="center" prop="key" v-if="columns.key.visible" />
      <el-table-column label="版本" align="center" prop="version" v-if="columns.version.visible" width="80" />
      <el-table-column label="状态" align="center" v-if="columns.suspended.visible">
        <template slot-scope="scope">
          <el-tag :type="scope.row.suspended ? 'warning' : 'success'">
            {{ scope.row.suspended ? '已挂起' : '运行中' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="160" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-play" @click="handleStart(scope.row)" v-hasPermi="['flowable:process:start']">启动</el-button>
          <el-button v-if="!scope.row.suspended" size="mini" type="text" icon="el-icon-pause" @click="handleSuspend(scope.row)" v-hasPermi="['flowable:process:suspend']">挂起</el-button>
          <el-button v-else size="mini" type="text" icon="el-icon-play" @click="handleActivate(scope.row)" v-hasPermi="['flowable:process:activate']">激活</el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <el-dialog :title="'启动流程'" :visible.sync="startOpen" width="400px" append-to-body>
      <el-form ref="startForm" :model="startForm" :rules="startRules" label-width="80px">
        <el-form-item label="流程名称">
          <el-input v-model="startForm.processName" disabled />
        </el-form-item>
        <el-form-item label="业务主键" prop="businessKey">
          <el-input v-model="startForm.businessKey" placeholder="请输入业务主键（可选）" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitStart">确定</el-button>
        <el-button @click="startOpen = false">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getProcessList, startProcess, suspendProcess, activateProcess } from '@/api/flowable/process'

export default {
  name: 'Process',
  data() {
    return {
      loading: true,
      showSearch: true,
      processList: [],
      total: 0,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: '',
        suspended: ''
      },
      startOpen: false,
      startForm: {
        processName: '',
        processDefinitionKey: '',
        businessKey: ''
      },
      startRules: {},
      columns: {
        id: { visible: true },
        name: { visible: true },
        key: { visible: true },
        version: { visible: true },
        suspended: { visible: true }
      },
      single: true,
      multiple: true,
      selectedRow: null
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      getProcessList().then(response => {
        this.processList = response.data || []
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
        name: '',
        suspended: ''
      }
      this.getList()
    },
    handleSelectionChange(val) {
      this.multiple = val.length > 0
      this.single = val.length === 1
      if (val.length === 1) {
        this.selectedRow = val[0]
      } else {
        this.selectedRow = null
      }
    },
    handleRowClick(row) {
      this.$refs.queryForm.clearSelection()
      this.selectedRow = row
    },
    handleStart(row) {
      const process = row || this.selectedRow
      if (!process) {
        this.$message.error('请选择一条流程')
        return
      }
      this.startForm = {
        processName: process.name,
        processDefinitionKey: process.key,
        businessKey: ''
      }
      this.startOpen = true
    },
    submitStart() {
      startProcess({
        processDefinitionKey: this.startForm.processDefinitionKey,
        businessKey: this.startForm.businessKey
      }).then(response => {
        if (response.code === 200) {
          this.$message.success(response.msg)
          this.startOpen = false
          this.getList()
        } else {
          this.$message.error(response.msg)
        }
      })
    },
    handleSuspend(row) {
      const process = row || this.selectedRow
      if (!process) {
        this.$message.error('请选择一条流程')
        return
      }
      this.$confirm('确定挂起选中的流程吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        suspendProcess({ processDefinitionId: process.id }).then(response => {
          if (response.code === 200) {
            this.$message.success(response.msg)
            this.getList()
          } else {
            this.$message.error(response.msg)
          }
        })
      })
    },
    handleActivate(row) {
      const process = row || this.selectedRow
      if (!process) {
        this.$message.error('请选择一条流程')
        return
      }
      activateProcess({ processDefinitionId: process.id }).then(response => {
        if (response.code === 200) {
          this.$message.success(response.msg)
          this.getList()
        } else {
          this.$message.error(response.msg)
        }
      })
    }
  }
}
</script>