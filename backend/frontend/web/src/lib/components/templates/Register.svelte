<script lang="ts">
	import type { IEducationalInstitution } from '$lib/api/queries/list-educational-institutions/list-educational-institutions.interface';
	import Button from '$lib/components/atoms/Button.svelte';
	import RegisterForm from '../molecules/forms/RegisterForm.svelte';
	import { toast } from 'svoast';

	export let educationalInstitutions: IEducationalInstitution[];
	export let registerUserLoading: boolean;
	export let registerUser: (data: Record<string, string>) => void;

	let formIsValid: boolean = false;
	let formValues: Record<string, string> = {
		name: '',
		phone: '',
		email: '',
		cpf: '',
		birthDate: '',
		institutionId: '',
		password: '',
		ra: ''
	};

	function handleOnRegisterUser() {
		if (!formIsValid) {
			toast.error('Preencha todos os campos.', { closable: true });
			return;
		}

		registerUser(formValues);
	}
</script>

<section class="flex h-full flex-col items-center justify-center gap-8 px-5 py-4 xl:px-15">
	<h1>Para começar sua jornada <br /> Crie uma conta</h1>
	<div>
		<RegisterForm bind:isValid={formIsValid} bind:formValues {educationalInstitutions} />
		<p class="redirect-text">Já tenho conta - <a href="/auth/login">login</a></p>
	</div>
	<Button
		text="Criar Conta"
		onClick={handleOnRegisterUser}
		disabled={!formIsValid}
		loading={registerUserLoading}
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

	.redirect-text {
		font-size: 12px;
		margin-top: 5px;
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
