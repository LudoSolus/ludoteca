<script lang="ts">
	import { toast } from 'svoast';
	import Button from '../atoms/Button.svelte';
	import OTPInput from '../atoms/OTPInput.svelte';

	export let onInsertToken: (token: string) => void;

	let userToken = '';

	function onCompleteInputCode(token: string) {
		userToken = token;
	}

	function handleGoToChangePassword() {
		if (userToken.length <= 0) {
			toast.error('Preencha o código corretamente.');
			return;
		}

		onInsertToken(userToken);
	}
</script>

<div class="flex w-full flex-col items-center gap-6 sm:gap-10">
	<h1 class="text-center text-3xl">Recuperar Acesso</h1>
	<p>Agora, insira o código que te enviamos por e-mail para criar uma nova senha.</p>
	<div class="flex w-fit max-w-full flex-col gap-2">
		<OTPInput size={6} onComplete={onCompleteInputCode} />
		<a class="redirect-text text-[12px]" href="/auth/login">Voltar para o login</a>
	</div>
	<div class="w-full sm:max-w-50">
		<Button text="Enviar" width="100%" height="35px" onClick={handleGoToChangePassword} />
	</div>
</div>
