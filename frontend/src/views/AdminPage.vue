<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
  <div id="table">
    <v-app id="inspire">
      <v-data-table
              :headers="headers"
              :items="users"
              sort-by="role desc"
              class="elevation-1"
      >
        <template v-slot:top>
          <v-toolbar flat color="white">
            <v-toolbar-title>Users</v-toolbar-title>
            <v-divider class="mx-4" inset vertical></v-divider>
            <v-spacer></v-spacer>
          </v-toolbar>
        </template>

        <template v-slot:item.role="props">
          <v-edit-dialog
                  :return-value.sync="props.item.role"
                  large
                  @save="save(props.item)"
          >
            <div v-if="props.item.role<50">USER</div>
            <div v-else>ADMIN</div>
            <template v-slot:input>
              <v-radio-group v-model="props.item.role">
                <v-radio
                        label="ADMIN"
                        value= 99
                ></v-radio>
                <v-radio
                        label="USER"
                        value= 1
                ></v-radio>
              </v-radio-group>
            </template>
          </v-edit-dialog>
        </template>

      </v-data-table>
    </v-app>
  </div>
</template>

<script>
  import axios from 'axios';
  import config from '../store/config'
  import {mapGetters} from 'vuex';

  export default {
    name: "AdminPage",
    computed: {
      ...mapGetters(["getUser"])
    },
    async mounted() {
      let user = this.getUser;
      if (user.role < 50) {
        alert('해당 페이지에 접근 할 수 없습니다.');
        this.$router.push('/');
      }

      let response = await axios.get(this.auth_server + '/admin/users', {
        headers: {
          Authorization: 'Bearer ' + localStorage.getItem('accessToken')
        }
      });

      if(!response.data.success) {
        if(!await this.$store.dispatch('refreshToken')){
          this.$router.push('/');
          return;
        }

        response = await axios.get(this.auth_server + '/admin/users', {
          headers: {
            Authorization: 'Bearer ' + localStorage.getItem('accessToken')
          }
        });
      }
      this.users = response.data.data;
    },
    data() {
      return {
        auth_server: config.AUTH_HOST,
        dialog: false,
        headers: [
          {text: 'id', value: 'id'},
          {text: 'email', value: 'email'},
          {text: 'nickname', value: 'nickname'},
          {text: 'role', value: 'role'}
        ],
        users: []
      }
    },
    methods: {
      async save (item) {
        let payload = { id: item.id, role: item.role };

        let response = await axios.put(this.auth_server + '/admin/users/update/role', payload, {
          headers: {
            Authorization: 'Bearer ' + localStorage.getItem('accessToken')
          }
        });

        if(!response.data.success) {
          if(!await this.$store.dispatch('refreshToken')){
            this.$router.push('/');
            return;
          }

          response = await axios.put(this.auth_server + '/admin/users/update/role', payload, {
            headers: {
              Authorization: 'Bearer ' + localStorage.getItem('accessToken')
            }
          });
        }

        alert(response.data.message);
      }
    },
  }
</script>
<style>
  #table {
    padding: 1%;
  }
</style>