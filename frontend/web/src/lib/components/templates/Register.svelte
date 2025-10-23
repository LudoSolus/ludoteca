<script lang="ts">
	import Button from '$lib/components/atoms/Button.svelte';
	import RegisterForm from '../molecules/forms/RegisterForm.svelte';
	import { toast } from 'svoast';

	let formIsValid: boolean = false;
	let formValues: Record<string, string> = {
		name: '',
		phone: '',
		email: '',
		cpf: '',
		birthDate: '',
		institute: '',
		password: '',
		ra: ''
	};

	async function launchToast() {
		toast.success('Conta criada com sucesso!', {closable: true});
	}
</script>

<section class="flex h-full flex-col items-center justify-center gap-10 px-5 py-4 xl:px-15">
	<h1>Para começar sua jornada <br /> Crie uma conta</h1>
	<RegisterForm bind:isValid={formIsValid} bind:formValues />
	<Button
		text="Criar Conta"
		onClick={launchToast}
		disabled={false}
		loading={true}
		width="250px"
		height="40px"
	/>
</section>

<style>
	h1 {
		width: 100%;
		font-size: 18px;
		font-weight: 800;
	}

	@media (min-width: 410px) {
		h1 {
			font-size: 22px;
		}
	}

	@media (min-width: 480px) {
		h1 {
			font-size: 26px;
		}
	}

	@media (min-width: 725px) {
		h1 {
			font-size: 36px;
			font-weight: 900;
		}
	}
</style>
