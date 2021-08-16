<template>
  <v-container>
    <v-row align="center" justify="center">
      <vs-input
        v-model="accessKey"
        placeholder="accessKey"
        class="inputs"
      >
        <template #icon>
          <i class='bx bx-lock-open-alt'></i>
        </template>
      </vs-input>
      <vs-input
        v-model="secretKey"
        placeholder="secretKey"
        class="inputs"
      >
        <template #icon>
            <i class='bx bx-lock-open-alt'></i>
          </template>
      </vs-input>
      <v-spacer/>
      <vs-button @click="getAccount()"  vs-type="flex" vs-justify="center" vs-align="center">
        업비트 계좌조회
      </vs-button>
    </v-row>
    <v-row class="my-4">
      <vs-table v-if="accountInfo !== null">
          <template #thead>
            <vs-tr>
              <vs-th>
                이름
              </vs-th>
              <vs-th>
                비중
              </vs-th>
              <vs-th>
                보유량
              </vs-th>
              <vs-th>
                구매평균단가
              </vs-th>
              <vs-th>
                구매통화
              </vs-th>
            </vs-tr>
          </template>
          <template #tbody>
            <vs-tr
              :key="i"
              v-for="(item, i) in accountInfo"
            >
              <vs-td>
                <v-row justify="start" align="center" class="my-2">
                <vs-avatar class="mr-2">
                  <img :src="`/crypto/` + item.currency +`.png`" alt="">
                </vs-avatar>
                {{ item.currency }}
                </v-row>
              </vs-td>
              <vs-td>
              {{ item.balance }}
              </vs-td>
              <vs-td>
              {{ item.locked }}
              </vs-td>
              <vs-td>
              {{ item.avg_buy_price }}
              </vs-td>
              <vs-td>
              {{ item.unit_currency }}
              </vs-td>

              <template #expand>
                <div class="con-content">
                  <div>
                    <vs-avatar>
                      <img :src="`/crypto/` + item.currency +`.png`" alt="">
                    </vs-avatar>
                  </div>
                </div>
              </template>
            </vs-tr>
          </template>
        </vs-table>
    </v-row>
  </v-container>
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
      const loading = this.$vs.loading()
      this.$axios.get('http://localhost:8080/account',{
        params: {
          accessKey: this.accessKey,
          secretKey: this.secretKey
        }
      }).then(res=>{
        this.accountInfo = res.data
        loading.close()
      }).catch(err=>{
        console.log(err)
        loading.close()
      })
    }
  }
}
</script>
<style lang="scss">
.inputs{
  padding:3px;
}
</style>

 