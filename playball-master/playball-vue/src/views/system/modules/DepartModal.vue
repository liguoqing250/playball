<template>
  <a-modal
    :title="title"
    :width="800"
    :ok=false
    :visible="visible"
    :confirmLoading="confirmLoading"
    :okButtonProps="{ props: {disabled: disableSubmit} }"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">

    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="球馆(商家)名称"
          :hidden="false"
          hasFeedback >
          <a-input id="departName" placeholder="请输入球馆(商家)名称" v-decorator="['departName', validatorRules.departName ]"/>
        </a-form-item>
        <!--
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" :hidden="seen" label="上级部门" hasFeedback>
        <a-tree-select
          style="width:100%"
          :dropdownStyle="{maxHeight:'200px',overflow:'auto'}"
          :treeData="departTree"
          v-model="model.parentId"
          placeholder="请选择上级部门"
          :disabled="condition">
        </a-tree-select>
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="机构类型">
          <template v-if="seen">
          <a-radio-group v-decorator="['orgCategory',validatorRules.orgCategory]" placeholder="请选择机构类型">
            <a-radio value="1">
              公司
            </a-radio>
          </a-radio-group>
          </template>
          <template v-else>
            <a-radio-group v-decorator="['orgCategory',validatorRules.orgCategory]" placeholder="请选择机构类型">
              <a-radio value="2">
                部门
              </a-radio>
              <a-radio value="3">
                岗位
              </a-radio>
            </a-radio-group>
          </template>
        </a-form-item>
        -->
        <a-form-item label="场馆图片" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-image-upload class="avatar-uploader" text="上传" v-model="fileList" ></j-image-upload>
        </a-form-item>

        <!--  JDate -->
        <!--
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="营业时间">
          <j-date :showTime="true" dateFormat="YYYY-MM-DD HH:mm:ss" v-decorator="['openTime', {} ]"/>
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="打烊时间">
          <j-date :showTime="true" dateFormat="YYYY-MM-DD HH:mm:ss" v-decorator="['closeTime', {} ]"/>
        </a-form-item>
        -->

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="营业时间">
          <a-time-picker v-model="dataTime.defaultStartTime" @change="startOnChange"
                         :default-value="moment('00:00', 'HH:mm')" format="HH:mm"/>
        </a-form-item>


        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="打烊时间">
          <a-time-picker v-model="dataTime.defaultCloseTime" @change="closeOnChange"
                         :default-value="moment('00:00', 'HH:mm')" format="HH:mm" />
        </a-form-item>


        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="手机号">
          <a-input placeholder="请输入手机号" v-decorator="['mobile',validatorRules.mobile]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="银行卡号">
          <a-input placeholder="请输入银行卡号" v-decorator="['bankCard', {'initialValue':''}]"/>
        </a-form-item>


        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="选择地址">
            <j-area-linkage v-model="areaLinkage" type="cascader"/>
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="详细地址">
          <a-input placeholder="请输入详细地址" v-decorator="['address', {'initialValue':''}]"/>
        </a-form-item>

        <!--
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="传真">
          <a-input placeholder="请输入传真" v-decorator="['fax', {}]"  />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="排序">
          <a-input-number v-decorator="[ 'departOrder',{'initialValue':0}]" />
        </a-form-item>
      -->

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="备注">
          <a-textarea placeholder="请输入备注" v-decorator="['memo', {}]"  />
        </a-form-item>

        <!--
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="地图">
            <div>
              <a-row type="flex" :gutter="16">
                <a-col :md="15" :sm="24">
                  <a-input id="serchAddr" placeholder="请输入球馆地址" />
                </a-col>
                <a-col :md="5" :sm="24">
                  <a-button @click="SearchAddress" type="default" htmlType="button">搜索</a-button>
                </a-col>
              </a-row>
            </div>
            <div id="mapContainer" style="width:500px;height:400px;"></div>
        </a-form-item>
        -->

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import moment from 'moment'
  import { httpAction } from '@/api/manage'
  import { queryIdTree } from '@/api/api'
  import pick from 'lodash.pick'
  import ATextarea from 'ant-design-vue/es/input/TextArea'
  import JDate from '@/components/jeecg/JDate'
  import JImageUpload from '@/components/jeecg/JImageUpload'
  import JAreaLinkage from '@comp/jeecg/JAreaLinkage'

  export default {
    name: "SysDepartModal",
    components: {
      moment,
      ATextarea,
      JDate,
      JImageUpload,
      JAreaLinkage
    },
    data () {
      return {
        departTree:[],
        orgTypeData:[],
        phoneWarning:'',
        departName:"",
        title:"操作",
        seen:false,
        visible: false,
        condition:true,
        disableSubmit:false,
        model: {},
        menuhidden:false,
        menuusing:true,
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },

        dataTime:{
            defaultStartTime: null,
            defaultCloseTime: null
        },
        openTime:'',
        closeTime:'',
        longitude:'',// 经度
        latitude:'',//纬度

        areaLinkage:'',
        jdate: {},
        fileList:[],
        confirmLoading: false,
        form: this.$form.createForm(this),

        validatorRules:{
        departName:{rules: [{ required: true, message: '请输入商家(球馆)名称!' }]},
         mobile:{rules: [{validator:this.validateMobile}]}
        },

        url: {
          add: "/sys/sysDepart/add",
        },
        dictDisabled:true,
      }
    },
    created () {
    },
    methods: {
      moment,
      startOnChange(time, timeString){
        this.dataTime.defaultStartTime = time
        this.openTime = timeString;
      },

      closeOnChange(time, timeString){
        this.dataTime.defaultCloseTime = time
        this.closeTime = timeString;
      },

      /*createMap() {
        //步骤：定义map变量 调用 qq.maps.Map() 构造函数   获取地图显示容器
        //设置地图中心点
        var myLatlng = new qq.maps.LatLng(26.556864,106.703179);
        //定义工厂模式函数
        var myOptions = {
          zoom: 8,               //设置地图缩放级别
          center: myLatlng,      //设置中心点样式
          mapTypeId: qq.maps.MapTypeId.ROADMAP  //设置地图样式详情参见MapType
        }
        //获取dom元素添加地图信息
        setTimeout(() => {
          var map = new qq.maps.Map(document.getElementById("mapContainer"), myOptions);

          //给地图添加点击事件
          //并获取鼠标点击的经纬度
          qq.maps.event.addListener(map, 'click', function(event) {
            this.longitude = event.latLng.getLat();
            this.latitude = event.latLng.getLng();

            alert("经度:"+this.longitude+","+"纬度:"+this.latitude);
          });
        }, 5)
      },
      SearchAddress(){
      },*/

      loadTreeData(){
        var that = this;
        queryIdTree().then((res)=>{
          if(res.success){
            that.departTree = [];
            for (let i = 0; i < res.result.length; i++) {
              let temp = res.result[i];
              that.departTree.push(temp);
            }
          }
        })
      },
      add (depart) {
        /*if(depart){
          this.seen = false;
          this.dictDisabled = false;
        }else{
          this.seen = true;
          this.dictDisabled = true;
        }*/
        this.edit(depart);
      },
      edit (record) {
          this.form.resetFields();
          //this.model = Object.assign({}, {});
          this.visible = true;
          this.loadTreeData();
          this.createMap();
          /*this.model.parentId = record!=null?record.toString():null;
          if(this.seen){
            this.model.orgCategory = '1';
          }else{
            this.model.orgCategory = '2';
          }*/
          this.$nextTick(() => {
            this.form.setFieldsValue(pick(this.model,'openTime','closeTime','departNameAbbr','mobile','bankCard','address','memo','imageUrl'))
          });
      },
      close () {
        this.$emit('close');
        this.disableSubmit = false;
        this.visible = false;
      },
      handleOk () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let formData = Object.assign(this.model, values);
            formData.imageUrl = that.fileList;
            formData.province = that.areaLinkage.substring(0,2)
            formData.city = that.areaLinkage.substring(2,4)
            formData.district = that.areaLinkage.substring(4,6)
            formData.openTime = that.openTime
            formData.closeTime = that.closeTime

            //时间格式化
            console.log("触发表单验证",formData)
            httpAction(this.url.add,formData,"post").then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.loadTreeData();
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })

          }
        })
      },
      handleCancel () {
        this.close()
      },
      validateMobile(rule,value,callback){
        if (!value || new RegExp(/^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\d{8}$/).test(value)){
          callback();
        }else{
          callback("您的手机号码格式不正确!");
        }

      }
    }
  }
</script>

<style scoped>

</style>