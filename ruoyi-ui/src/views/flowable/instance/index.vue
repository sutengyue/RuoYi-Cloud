<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="流程实例ID" prop="processInstanceId">
        <el-input v-model="queryParams.processInstanceId" placeholder="请输入流程实例ID" clearable style="width: 240px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-stop" size="mini" :disabled="single" @click="handleTerminate" v-hasPermi="['flowable:instance:terminate']">终止流程</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="instanceList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="50" align="center" />
      <el-table-column label="实例ID" align="center" prop="id" v-if="columns.id.visible" />
      <el-table-column label="流程定义ID" align="center" prop="processDefinitionId" v-if="columns.processDefinitionId.visible" />
      <el-table-column label="业务主键" align="center" prop="businessKey" v-if="columns.businessKey.visible" />
      <el-table-column label="启动时间" align="center" prop="startTime" v-if="columns.startTime.visible" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" v-if="columns.ended.visible">
        <template slot-scope="scope">
          <el-tag :type="scope.row.ended ? 'danger' : 'success'">
            {{ scope.row.ended ? '已结束' : '运行中' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="120" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-if="!scope.row.ended" size="mini" type="text" icon="el-icon-stop" @click="handleTerminate(scope.row)" v-hasPermi="['flowable:instance:terminate']">终止</el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />
  </div>
</template>

<script>
import { getInstanceList, terminateInstance } from '@/api/flowable/instance'

export default {
  name: 'Instance',
  data() {
    return {
      loading: true,
      showSearch: true,
      instanceList: [],
      total: 0,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        processInstanceId: ''
      },
      columns: {
        id: { visible: true },
        processDefinitionId: { visible: true },
        businessKey: { visible: true },
        startTime: { visible: true },
        ended: { visible: true }
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
      getInstanceList().then(response => {
        this.instanceList = response.data || []
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
        processInstanceId: ''
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
    handleTerminate(row) {
      const instance = row || this.selectedRow
      if (!instance) {
        this.$message.error('请选择一条流程实例')
        return
      }
      this.$confirm('确定终止选中的流程实例吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'danger'
      }).then(() => {
        terminateInstance({ processInstanceId: instance.id }).then(response => {
          if (response.code === 200) {
            this.$message.success(response.msg)
            this.getList()
          } else {
            this.$message.error(response.msg)
          }
        })
      })
    }
  }
}
</script>