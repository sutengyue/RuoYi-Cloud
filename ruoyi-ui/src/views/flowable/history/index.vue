<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="流程实例ID" prop="processInstanceId">
        <el-input v-model="queryParams.processInstanceId" placeholder="请输入流程实例ID" clearable style="width: 240px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="创建时间">
        <el-date-picker v-model="dateRange" style="width: 240px" value-format="yyyy-MM-dd" type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="historyList">
      <el-table-column label="实例ID" align="center" prop="id" v-if="columns.id.visible" />
      <el-table-column label="流程定义ID" align="center" prop="processDefinitionId" v-if="columns.processDefinitionId.visible" />
      <el-table-column label="业务主键" align="center" prop="businessKey" v-if="columns.businessKey.visible" />
      <el-table-column label="开始时间" align="center" prop="startTime" v-if="columns.startTime.visible" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束时间" align="center" prop="endTime" v-if="columns.endTime.visible" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="耗时(毫秒)" align="center" prop="duration" v-if="columns.duration.visible" />
    </el-table>
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />
  </div>
</template>

<script>
import { getHistoryList } from '@/api/flowable/history'

export default {
  name: 'History',
  data() {
    return {
      loading: true,
      showSearch: true,
      historyList: [],
      total: 0,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        processInstanceId: ''
      },
      dateRange: [],
      columns: {
        id: { visible: true },
        processDefinitionId: { visible: true },
        businessKey: { visible: true },
        startTime: { visible: true },
        endTime: { visible: true },
        duration: { visible: true }
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      getHistoryList().then(response => {
        this.historyList = response.data || []
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
      this.dateRange = []
      this.getList()
    }
  }
}
</script>