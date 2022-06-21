Vue.createApp({

    data() {
        return {
            cliente: [], //PROPIEDADES
            cuentas: [],
            tarjetas: [],
            prestamos: [],
            transacciones: [].sort(function(a, b){return b - a}),

            cardHolder: [],

            type: "",
            cardColor: "",

            cantidad: '',
            name: '',

            description: "",
            originAccount: "",
            destinationAccount: "",


        }

    },



    created() {
        axios.get("http://localhost:8080/api/clients/current")
            .then(data => {
                this.cliente = data.data //este muestra toda la data o Json
                this.cuentas = this.cliente.accounts
                this.prestamos = this.cliente.loans
                this.tarjetas = this.cliente.cards
                this.transacciones = this.cuentas[0].transactions //preguntar como recorrer cuentas para mostrar las transacciones
                // console.log(this.cliente)
                // console.log(this.cuentas)
                // console.log(this.cuentas)
                // console.log(this.transacciones)
            })
    },

    methods: {
        signOut() {
            axios.post('/api/logout')
//                .then(response => console.log('signed out!!!'))
                .then(response => window.location.href = "http://localhost:8080/web/index.html")
        },

        obtenerCuentaOrigen(cuentaNumber) {
            this.originAccount = cuentaNumber
            console.log(this.originAccount)
        },

        transferencia() {
            axios.post('/api/transactions', `cantidad=${this.cantidadAEnviar}&description=${this.description}&originAccount=${this.originAccount}&destinationAccount=${this.destinationAccount}`, { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                .then(response => console.log("transferencia enviada"))
                .then(response => { window.location.reload() })
        },



    },







    computed: {
        // comprobar() {
        //     return this.cantidad.length > 2 ? true : false
        // }

    },





}).mount('#app');