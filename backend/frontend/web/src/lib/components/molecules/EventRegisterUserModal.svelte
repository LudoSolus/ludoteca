<script lang="ts">
	import { toast } from 'svoast';
	import FormInput from '../atoms/FormInput.svelte';
	import Modal from '../atoms/Modal.svelte';

	export let isOpen: boolean;
	export let isLoading: boolean;
	export let onEventRegisterUser: (publicUserId: string) => void;

	let publicUserIdInput: string = '';

	$: if (isOpen !== undefined) {
		publicUserIdInput = '';
	}

	function close() {
		isOpen = false;
	}

	function handleRegisterButton() {
		if (publicUserIdInput.length < 1) {
			toast.error('Você deve colocar o id público do usuário.', { closable: true });
			return;
		}

		if (publicUserIdInput.length < 4 || publicUserIdInput.length > 4) {
			toast.error('O Id deve ter 4 caracteres.', { closable: true });
			return;
		}

		onEventRegisterUser(publicUserIdInput);
	}
</script>

{#if isOpen}
	<Modal onConfirm={handleRegisterButton} onClose={close} {isLoading} confirmButtonText="Registrar">
		<div class="flex flex-col gap-6">
			<p class="text-center text-lg font-medium sm:text-xl">
				Digite o Id público do usuário que deseja registrar no evento
			</p>
			<FormInput
				label={'Id Público'}
				height="90px"
				bind:value={publicUserIdInput}
				onInput={(v) => {}}
				isUppercase
			/>
		</div>
	</Modal>
{/if}
