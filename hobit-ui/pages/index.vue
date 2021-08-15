<template>

  <div class="center">
    <div class="center content-inputs">
    <vs-input
      label="공개키"
      v-model="accessKey"
      placeholder="accessKey"
      class="inputs"
    >
      <template #icon>
        <i class='bx bx-lock-open-alt'></i>
      </template>
    </vs-input>
    <vs-input
      label="비밀키"
      v-model="secretKey"
      placeholder="secretKey"
      class="inputs"
    >
      <template #icon>
          <i class='bx bx-lock-open-alt'></i>
        </template>
    </vs-input>
    </div>
    <vs-button @click="getAccount()">
      업비트 계좌조회
    </vs-button>
    {{accountInfo}}
  </div>
</template>

<script>
export default {
  components: {
    
  },
  data:() => ({
    accountInfo:null,
    accessKey:'',
    secretKey:''
  }),
  methods:{
    getAccount() {
      this.$axios.get('http://localhost:8080/account',{
        params: {
          accessKey: this.accessKey,
          secretKey: this.secretKey
        }
      }).then(res=>{
        this.accountInfo = res
      }).catch(err=>{
        console.log(err)
      })
    }
  }
}
</script>
<style lang="scss">
.inputs{
  padding:16px;
}
</style>

 