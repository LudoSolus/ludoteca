<script lang="ts">
	import Button from '$lib/components/atoms/Button.svelte';
	import RegisterForm from '../molecules/forms/RegisterForm.svelte';

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
</script>

<section class="flex h-full flex-col items-center justify-center gap-10 px-15 py-4">
	<h1>Para começar sua jornada <br /> Crie uma conta</h1>
	<RegisterForm bind:isValid={formIsValid} bind:formValues />
	<Button
		text="Criar Conta"
		onClick={() => {
			console.log('clicado');
		}}
		disabled={!formIsValid}
		width="250px"
		height="40px"
	/>
</section>

<style>
	section {
		min-width: fit-content;
		width: 100vw;
		background: var(--card-background-color);
		border-top-left-radius: 20px;
		border-bottom-left-radius: 20px;
		box-shadow: -2px 0px 4px 0px rgba(0, 0, 0, 0.7);
	}

	h1 {
		width: 100%;
		font-size: 36px;
		font-weight: 900;
	}
</style>
