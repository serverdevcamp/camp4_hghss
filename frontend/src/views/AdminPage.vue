<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
  <div id="table">
    <v-app id="inspire">
      <v-data-table
              :headers="headers"
              :items="users"
              sort-by="role"
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
            <div>{{ props.item.role }}</div>
            <template v-slot:input>
              <v-radio-group v-model="props.item.role">
                <v-radio
                        label="ADMIN"
                        value="ADMIN"
                ></v-radio>
                <v-radio
                        label="USER"
                        value="USER"
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
  import {mapGetters} from 'vuex';

  export default {
    name: "AdminPage",
    computed: {
      ...mapGetters(["getUser"])
    },
    async mounted() {
      let user = this.getUser;
      if (user.role !== 'ADMIN') {
        alert('해당 페이지에 접근 할 수 없습니다.');
        this.$router.push('/');
      }

      let response = await axios.get('http://localhost:8000/admin/users', {
        headers: {
          Authorization: 'Bearer ' + localStorage.getItem('accessToken')
        }
      });

      this.users = response.data.data;
    },
    data() {
      return {
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
        let config = {headers: { Authorization: 'Bearer ' + localStorage.getItem('accessToken') }};
        let response = await axios.put('http://localhost:8000/admin/users/update/role', payload, config);

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