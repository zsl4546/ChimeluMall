<template>
  <div>
    <template>
      <el-table
        :data="tableData"
        element-loading-text="拼命加载中"
        element-loading-spinner="el-icon-loading"
        element-loading-background="rgba(0, 0, 0, 0.8)"
        style="width: 100%">
        <el-table-column
          width="280">
          <template slot-scope="scope">
            <el-image :src="'http://localhost:7502/files/'+scope.row.fileName"></el-image>
          </template>
        </el-table-column>
        <el-table-column
          label="商品名"
          width="180">
          <template slot-scope="scope">
            <div slot="reference" class="name-wrapper">
              <el-tag size="medium">{{ scope.row.titleName }}</el-tag>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          label="价格"
          width="280"
          prop="price"/>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="danger"
              @click="handleDelete(scope.$index, scope.row)">立即购买</el-button>
          </template>
        </el-table-column>
      </el-table>
    </template>
  </div>
</template>

<script>
  import { getCommodities } from '../api/manage'

  export default {
    name: 'Home',
    created () {
      const _this = this
      getCommodities().then(function (response) {
        _this.tableData = response.data;
      })
    },
    data() {
      return {
        tableData: []
      }
    },
    methods: {

    }
  }
</script>

