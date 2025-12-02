<script lang="ts">
	import { toast } from 'svoast';
	import Button from '../atoms/Button.svelte';
	import LoginForm from '../molecules/forms/LoginForm.svelte';

	export let loginUserLoading: boolean;
    export let loginUser: (data: Record<string, string>) => void;

	let formIsValid: boolean = false;
	let formValues: Record<string, string> = {
		email: '',
		password: ''
	};

	function handleOnLogin() {
		if (!formIsValid) {
			toast.error('Preencha todos os campos.', { closable: true });
			return;
		}

		loginUser(formValues);
	}
</script>

<section class="flex h-full flex-col items-center justify-center gap-12 px-5 py-4 xl:px-15">
	<h1>Bem vindo de volta</h1>
	<div class="flex flex-col">
		<LoginForm bind:isValid={formIsValid} bind:formValues />
		<a class="redirect-text w-fit" href="/forget-password">Esqueci minha senha</a>
		<p class="redirect-text">Não tenho conta - <a href="/auth/register">cadastrar</a></p>
	</div>
	<Button
		text="Entrar"
		onClick={handleOnLogin}
		disabled={!formIsValid}
		loading={loginUserLoading}
		width="200px"
		height="40px"
	/>
</section>

<style>
	h1 {
		width: 100%;
		font-size: 20px;
		font-weight: 800;
	}

	.redirect-text {
		font-size: 12px;
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
			font-size: 34px;
			font-weight: 900;
		}
	}
</style>
