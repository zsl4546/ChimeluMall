<template>
    <div>
      <template>
        <el-table
          :data="tableData"
          element-loading-text="拼命加载中"
          element-loading-spinner="el-icon-loading"
          element-loading-background="rgba(0, 0, 0, 0.8)"
          style="width: 100%">
<!--          <el-table-column-->
<!--            label="日期"-->
<!--            width="180">-->
<!--            <template slot-scope="scope">-->
<!--              <i class="el-icon-time"></i>-->
<!--              <span style="margin-left: 10px">{{ scope.row.date }}</span>-->
<!--            </template>-->
<!--          </el-table-column>-->
          <el-table-column
            label="姓名"
            width="180">
            <template slot-scope="scope">
                <div slot="reference" class="name-wrapper">
                  <el-tag size="medium">{{ scope.row.name }}</el-tag>
                </div>
            </template>
          </el-table-column>
          <el-table-column
            label="账户"
            width="280"
          prop="account"/>
          <el-table-column
            label="密码"
            width="180"
            prop="password"/>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button
                size="mini"
                @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
              <el-button
                size="mini"
                type="danger"
                @click="handleDelete(scope.$index, scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </template>
    </div>
</template>

<script>
  import { deleteUser, getUsers } from '../../api/manage'

  export default {
    name: 'account',
    created () {
      const _this = this
      getUsers().then(function (response) {
        _this.tableData = response.data.userAccounts;
      })
    },
    data() {
      return {
        tableData: []
      }
    },
    methods: {
      handleEdit(index, row) {
        console.log(index, row);
      },
      handleDelete(index, row) {
        const _this = this
        console.log("account: "+row.account);
        deleteUser(null, row.account).then(function () {
          getUsers().then(function (response) {
            _this.tableData = response.data.userAccounts;
          });
        })
        console.log(index, row);
      }
    }
  }
</script>

<style scoped>

</style>
