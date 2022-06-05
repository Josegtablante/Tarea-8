
Vue.createApp({

    data() {
        return {
            cliente: [], //PROPIEDADES
            Json: [],

            id: [],
            cuentas: [],
            cantidades: [],
            ballance: [],
            number: [],
            transactions: [],
        }
    },



    created() {
        const urlParams = new URLSearchParams(window.location.search);
        const id = urlParams.get('id');

        axios.get("http://localhost:8080/api/accounts/" + id)
            .then(data => {
                this.cliente = data.data //este muestra toda la data o Json
                this.cuentas = this.data.accounts //Entra en Json y muestra accounts
                this.transactions = data.data.transactions//Entra en Json y muestra accounts
                console.log(this.cliente)
                // console.log(this.cuentas)
                this.transactions = this.transactions.sort((a, b) => b.id - a.id)
            })
    },

    methods: {


    },

    computed: {

    },




}).mount('#app')