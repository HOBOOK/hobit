<template>
  <div class="app">

  <template>
    <div class="grid">
      <vs-row>
        <vs-col w="1">
          <vs-sidebar
            v-model="sideBar"
            open
            hover-expand
            reduce
          >
            <template #logo>
              <img src="/icon_img.png" alt="">
            </template>
            <vs-sidebar-item id="home">
              
              <template #icon>
                <i class='bx bx-home'></i>
              </template>
              <NuxtLink to="/" >홈</NuxtLink>
            </vs-sidebar-item>
            <vs-sidebar-item id="dashboard">
              <template #icon>
                <i class='bx bx-grid-alt'></i>
              </template>
              대시보드
            </vs-sidebar-item>
            <vs-sidebar-item id="Music">
              <template #icon>
                <i class='bx bxs-music'></i>
              </template>
              Music
            </vs-sidebar-item>
            <vs-sidebar-group>
              <template #header>
                <vs-sidebar-item arrow>
                  <template #icon>
                    <i class='bx bx-group'></i>
                  </template>
                  Social media
                </vs-sidebar-item>
              </template>

              <vs-sidebar-item id="Instagram">
                <template #icon>
                  <i class='bx bxl-instagram'></i>
                </template>
                Instagram
              </vs-sidebar-item>
              <vs-sidebar-item id="twitter">
                <template #icon>
                  <i class='bx bxl-twitter' ></i>
                </template>
                Twitter
              </vs-sidebar-item>
              <vs-sidebar-item id="Facebook">
                <template #icon>
                  <i class='bx bxl-facebook' ></i>
                </template>
                Facebook
              </vs-sidebar-item>
            </vs-sidebar-group>
            <template #footer>
              <vs-row justify="space-between">
                <vs-avatar>
                  <img src="/avatars/avatar-5.png" alt="">
                </vs-avatar>

                <vs-avatar badge-color="danger" badge-position="top-right">
                  <i class='bx bx-bell' ></i>

                  <template #badge>
                    28
                  </template>
                </vs-avatar>
              </vs-row>
            </template>
          </vs-sidebar>
        </vs-col>
        <vs-col vs-type="flex" vs-justify="center" vs-align="center" w="11">
          <div class="main-container center examplex">
            <vs-navbar center-collapsed square v-model="navBar" fixed shadowScroll paddingScroll>
              <template #left>
                <img src="/icon_img.png" alt="">
              </template>
              <vs-navbar-item :active="navBar === 'guide'" id="guide">
                Guide
              </vs-navbar-item>
              <vs-navbar-item :active="navBar === 'docs'" id="docs">
                Documents
              </vs-navbar-item>
              <vs-navbar-item :active="navBar === 'components'" id="components">
                Components
              </vs-navbar-item>
              <vs-navbar-item :active="navBar === 'license'" id="license">
                license
              </vs-navbar-item>
              <template #right>
                <vs-button flat >Login</vs-button>
                <vs-button>Get Started</vs-button>
              </template>
            </vs-navbar>

            <Nuxt class="app-container" :class="{active : pageLoad}"/>
            
          </div>
        </vs-col>
    </vs-row>
  </div>
  </template>


  </div>
</template>

<script>

export default{
  components: {
  },
  data:() => ({
    sideBar:false,
    navBar:'guide',
    pageLoad:true,
  }),
  methods:{
  },
  created(){
  },
  mounted(){
  },
  computed:{
  },
  watch:{
  },
  beforeDestroy () { 
  }
  
}

</script>

