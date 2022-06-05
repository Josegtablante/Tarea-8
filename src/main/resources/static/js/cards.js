Vue.createApp({

    data() {
        return {
            cliente: [], //PROPIEDADES
            
            cuentas:[],
            cantidades:[],
            ballance:[],
            number:[],
            prestamos:[],

            tarjetas:[],
            cardHolder:[],
            isCard:!false,

        };
    },



    created() {
        axios.get("http://localhost:8080/api/clients/current").then(data => {
                this.cliente = data.data //este muestra toda la data o Json
                this.cuentas = this.cliente.accounts
                this.prestamos = this.cliente.loans
                this.tarjetas = this.cliente.cards
                // console.log(this.cliente)
                // console.log(this.prestamos)
                // console.log(this.tarjetas)


                console.log(this.tarjetas[0].color)
                console.log(this.tarjetas[0].type)
                console.log(this.tarjetas)
                console.log(this.cliente)

                if (tarjetas.length >=5 ) {
                    this.isCard = false;
                }
        

            })
    },

    methods: {

        crearTarjetas(){
            axios.post('http://localhost:8080/api/clients/current/cards')
            .then(response => { console.log("Tarjeta Creada") })
            // .then(response => {if (this.crearCuenta.length >= 3) {window.alert("no puedes crear mas de tres cuentas")}})
            // .then(response => { location.reload() }) //como hacer para limitar la cuenta a solo 3 . pensar
        
        },
        signOut() {
            axios.post('/api/logout')
                .then(response => console.log('signed out!!!'))
                .then(response => window.location.href = "http://localhost:8080/web/index.html")
        },




    },

    computed: {

    },




}).mount('#app');