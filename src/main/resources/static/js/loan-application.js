const app = Vue.createApp({

    data() {
        return {
            cliente: [], //PROPIEDADES
            cuentas: [],
            prestamos: [],


            accountNumber: "",
            payments: 0.00,
            amount: 0.00,

            loanSeleccionado: [],
            loan: [],
            loans: [],
            nombrePrestamo: [],
        };
    },



    created() {
        axios.get('http://localhost:8080/api/clients/current/') //clientess registrados
            .then(data => {
                this.cliente = data.data //este muestra toda la data o Json
                this.cuentas = this.cliente.accounts
                this.prestamos = this.cliente.loans
                // console.log(this.cliente)
                // console.log(this.prestamos)

            })
        axios.get('http://localhost:8080/api/loan')
            .then(data => {
                this.loans = data.data //este muestra toda la data o Json
                this.prestamos = this.loans
          //      console.log(this.prestamos)

            })
    },

    methods: {

        signOut() {
            axios.post('/api/logout')
                .then(response => console.log('signed out!!!'))
                .then(response => window.location.href = "http://localhost:8080/web/index.html")
        },

        createLoan() {
            axios.post('/api/loans', { id: this.loanSeleccionado.id, amount: this.amount, payments: this.payments, accountNumber: this.accountNumber })
                .then(response => console.log("create"))
                .then(response => window.location.href = "http://localhost:8080/web/accounts.html")
        }




    },

    computed: {

    },




}).mount('#app');