<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="任务名称" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入任务名称" clearable style="width: 240px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="处理人" prop="assignee">
        <el-input v-model="queryParams.assignee" placeholder="请输入处理人" clearable style="width: 240px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-user" size="mini" :disabled="single" @click="handleClaim" v-hasPermi="['flowable:task:claim']">认领任务</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-check" size="mini" :disabled="single" @click="handleComplete" v-hasPermi="['flowable:task:complete']">完成任务</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="taskList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="50" align="center" />
      <el-table-column label="任务ID" align="center" prop="id" v-if="columns.id.visible" />
      <el-table-column label="任务名称" align="center" prop="name" v-if="columns.name.visible" :show-overflow-tooltip="true" />
      <el-table-column label="处理人" align="center" prop="assignee" v-if="columns.assignee.visible" />
      <el-table-column label="流程实例ID" align="center" prop="processInstanceId" v-if="columns.processInstanceId.visible" />
      <el-table-column label="优先级" align="center" prop="priority" v-if="columns.priority.visible" width="80" />
      <el-table-column label="创建时间" align="center" prop="createTime" v-if="columns.createTime.visible" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="160" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-user" @click="handleClaim(scope.row)" v-hasPermi="['flowable:task:claim']">认领</el-button>
          <el-button size="mini" type="text" icon="el-icon-check" @click="handleComplete(scope.row)" v-hasPermi="['flowable:task:complete']">完成</el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <el-dialog :title="'认领任务'" :visible.sync="claimOpen" width="400px" append-to-body>
      <el-form ref="claimForm" :model="claimForm" :rules="claimRules" label-width="80px">
        <el-form-item label="任务名称">
          <el-input v-model="claimForm.taskName" disabled />
        </el-form-item>
        <el-form-item label="处理人" prop="assignee">
          <el-input v-model="claimForm.assignee" placeholder="请输入处理人" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitClaim">确定</el-button>
        <el-button @click="claimOpen = false">取消</el-button>
      </div>
    </el-dialog>

    <el-dialog :title="'完成任务'" :visible.sync="completeOpen" width="400px" append-to-body>
      <el-form ref="completeForm" :model="completeForm" label-width="80px">
        <el-form-item label="任务名称">
          <el-input v-model="completeForm.taskName" disabled />
        </el-form-item>
        <el-form-item label="处理意见">
          <el-textarea v-model="completeForm.comment" placeholder="请输入处理意见（可选）" rows="3" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitComplete">确定</el-button>
        <el-button @click="completeOpen = false">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getTaskList, claimTask, completeTask } from '@/api/flowable/task'

export default {
  name: 'Task',
  data() {
    return {
      loading: true,
      showSearch: true,
      taskList: [],
      total: 0,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: '',
        assignee: ''
      },
      claimOpen: false,
      claimForm: {
        taskName: '',
        taskId: '',
        assignee: ''
      },
      claimRules: {
        assignee: [
          { required: true, message: '处理人不能为空', trigger: 'blur' }
        ]
      },
      completeOpen: false,
      completeForm: {
        taskName: '',
        taskId: '',
        comment: ''
      },
      columns: {
        id: { visible: true },
        name: { visible: true },
        assignee: { visible: true },
        processInstanceId: { visible: true },
        priority: { visible: true },
        createTime: { visible: true }
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
      getTaskList().then(response => {
        this.taskList = response.data || []
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
        assignee: ''
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
    handleClaim(row) {
      const task = row || this.selectedRow
      if (!task) {
        this.$message.error('请选择一条任务')
        return
      }
      this.claimForm = {
        taskName: task.name,
        taskId: task.id,
        assignee: ''
      }
      this.claimOpen = true
    },
    submitClaim() {
      claimTask({
        taskId: this.claimForm.taskId,
        assignee: this.claimForm.assignee
      }).then(response => {
        if (response.code === 200) {
          this.$message.success(response.msg)
          this.claimOpen = false
          this.getList()
        } else {
          this.$message.error(response.msg)
        }
      })
    },
    handleComplete(row) {
      const task = row || this.selectedRow
      if (!task) {
        this.$message.error('请选择一条任务')
        return
      }
      this.completeForm = {
        taskName: task.name,
        taskId: task.id,
        comment: ''
      }
      this.completeOpen = true
    },
    submitComplete() {
      completeTask({
        taskId: this.completeForm.taskId,
        comment: this.completeForm.comment
      }).then(response => {
        if (response.code === 200) {
          this.$message.success(response.msg)
          this.completeOpen = false
          this.getList()
        } else {
          this.$message.error(response.msg)
        }
      })
    }
  }
}
</script>