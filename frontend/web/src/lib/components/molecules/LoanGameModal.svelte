<script lang="ts">
	import { toast } from 'svoast';
	import FormInput from '../atoms/FormInput.svelte';
	import Modal from '../atoms/Modal.svelte';

	export let isOpen: boolean;
	export let gameName: string
	export let isLoading: boolean;
	export let onLoanGame: (publicUserId: string) => void;

	let publicUserIdInput: string = '';

	$: if (isOpen !== undefined) {
		publicUserIdInput = '';
	}

	function closeModal() {
		isOpen = false;
	}

	function handleLoanGame() {
		if (publicUserIdInput.length < 1) {
			toast.error('Você deve colocar o id público do usuário.', { closable: true });
			return;
		}

		if (publicUserIdInput.length < 4 || publicUserIdInput.length > 4) {
			toast.error('O Id deve ter 4 caracteres.', { closable: true });
			return;
		}

		onLoanGame(publicUserIdInput);
	}
</script>

{#if isOpen}
	<Modal onConfirm={handleLoanGame} onClose={closeModal} {isLoading} confirmButtonText="Emprestar">
		<div class="flex flex-col gap-6">
			<p class="text-center text-lg font-medium sm:text-xl">
				Emprestar {gameName} para
			</p>
			<FormInput
				label={'Id Público'}
				placeholder="Id público do usuário"
				height="90px"
				bind:value={publicUserIdInput}
				onInput={(v) => {}}
				isUppercase
			/>
		</div>
	</Modal>
{/if}
