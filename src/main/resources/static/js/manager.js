Vue.createApp({

  data() {
    return {
      loadData: [],
      Clientes: [],
      cliente: [], //revisar
      Json: [],


      firstName: "",
      lastName: "",
      email: "",

      // FIRST_NAME:[],
      // LAST_NAME:[],
      //aca pongo las variables que voy a invocar
    }
  },



  created() {
    axios.get(`http://localhost:8080/rest/clients`)
      .then(data => {
        this.loadData = data.data._embedded.cliente
        this.Json = data.data
        console.log(this.Json)
        console.log(this.loadData)

      })
  },

  methods: {
    enviarCliente() {
      axios.post(`http://localhost:8080/rest/clients`, {
        firstName: this.firstName,
        lastName: this.lastName,
        email: this.email,
        id: this.id,
      })
    },

    //Get para pedir datos
    //post para crear datos
    //delete para borrar datos
    //puth para modificar un objeto entero 
    //path para modificar solo una propiedad del objeto

    eliminarCliente(id){
      axios.delete(id)
      //  .then(function () {
      //    location.reload()
      //      .catch(error => console.log(error))
       // })

    },


    editarCliente(){
    },



    computed: {
    },

  }











}).mount('#app')