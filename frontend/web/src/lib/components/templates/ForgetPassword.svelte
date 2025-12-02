<script lang="ts">
	import Button from '../atoms/Button.svelte';
	import FormInput from '../atoms/FormInput.svelte';

	export let onSendEmail: (email: string) => void;
	export let isLoading: boolean;

	let emailInput = '';
</script>

<div class="flex w-full flex-col items-center gap-6 sm:gap-10">
	<h1 class="text-center text-3xl">Recuperar Acesso</h1>
	<p>
		Para recuperar a sua senha, informe o seu endereço de e-mail que nós enviaremos um link para
		alteração de senha.
	</p>
	<div class="flex w-full flex-col gap-2">
		<FormInput
			label="E-mail"
			placeholder="Digite seu e-mail"
			bind:value={emailInput}
			onInput={(v) => {}}
		/>
		<a class="redirect-text text-[12px] w-fit" href="/auth/login">Voltar para o login</a>
	</div>
	<div class="w-full sm:max-w-50">
		<Button
			text="Enviar"
			width="100%"
			height="35px"
			loading={isLoading}
			onClick={() => {
				onSendEmail(emailInput);
			}}
		/>
	</div>
</div>
