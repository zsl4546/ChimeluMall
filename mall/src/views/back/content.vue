<template>
  <div>
    <el-button style="float: left" @click="addFormVisible = true">添加</el-button>
    <el-dialog title="添加商品" :visible.sync="addFormVisible">
      <el-form :model="addForm">
        <el-form-item label="商品名">
          <el-input v-model="addForm.titleName"></el-input>
        </el-form-item>
        <el-form-item label="价格">
          <el-input v-model="addForm.price" type="number"></el-input>
        </el-form-item>
        <el-form-item label="库存">
          <el-input v-model="addForm.stock" type="number"></el-input>
        </el-form-item>
        <el-upload
          class="upload-demo"
          action="/com/file/upload"
          :on-change="handleChange"
          :on-preview="handlePreview"
          :on-remove="handleRemove"
          :limit="1"
          :file-list="fileList"
          list-type="picture">
          <el-button size="small" type="primary">上传商品图片</el-button>
          <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
        </el-upload>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="add">确 定</el-button>
      </div>
    </el-dialog>
    <template>
      <el-table
        :data="tableData"
        element-loading-text="拼命加载中"
        element-loading-spinner="el-icon-loading"
        element-loading-background="rgba(0, 0, 0, 0.8)"
        style="width: 100%"
      v-if="1===1">
        <el-table-column
          label="图片"
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
        <el-table-column
          label="库存"
          width="280"
          prop="stock"/>
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
  import { addCommodity, deleteFile, getCommodities } from '../../api/manage'

  export default {
    name: '内容管理',
    created () {
      const _this = this
      getCommodities().then(function (response) {
        _this.tableData = response.data;
      })
    },
    data() {
      return {
        tableData: [],
        addFormVisible: false,
        addForm:{
          titleName: '',
          price: '',
          stock: ''
        },
        fileList: [ ],
      }
    },
    methods: {
      add(){
        const _this = this
        if (_this.addForm.titleName === '' || _this.addForm.price === '' || _this.fileList.length === 0) {
          console.log(_this.addForm.titleName)
          console.log(_this.addForm.price)
          console.log(_this.fileList)
          this.$notify.error({
            title: '错误',
            message: '请将表单填写完整'
          });
        } else {
          let data = {titleName: _this.addForm.titleName, price: _this.addForm.price, fileName: _this.fileList[0].name, stock: _this.addForm.stock}
          addCommodity(JSON.stringify(data)).then(function (response) {
            console.log("添加成功！")
            console.log(response)
            _this.addFormVisible = false
            if (response.code === "000000") {
              _this.$message({
                message: '添加成功！',
                type: 'success'
              });
              _this.tableData.push(data)
              // getCommodities().then(function (response) {
              //   _this.tableData = response.data;
              // })
            }
          })
        }
      },
      handleRemove(file, fileList) {
        deleteFile({fileName:file.name}).then(function (response) {
          console.log("删除成功！")
        })
      },
      handlePreview(file) {
        console.log(file);
      },
      handleChange(file, fileList){
        console.log(fileList)
        this.fileList = fileList
      },
      handleDelete(index, row){
        const _this = this
        deleteFile(row).then(function (response) {
          let index = _this.tableData.findIndex(item => {
            if ( item.id === row.id) {
              return true;
            }
          })
          _this.tableData.splice(index,1)
      })
    }
    }
  }
</script>

<style scoped>

</style>
