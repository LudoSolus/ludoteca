<script lang="ts">
	import { toast } from 'svoast';
	import Button from '../atoms/Button.svelte';
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
	<!-- svelte-ignore a11y_no_static_element_interactions -->
	<Modal onClickOuside={close}>
		<!-- svelte-ignore a11y_click_events_have_key_events -->
		<div
			class="modal-container flex max-w-9/10 flex-col items-center gap-2 rounded-2xl p-5 sm:gap-8 sm:px-15 sm:py-10 md:max-w-170"
			onclick={() => event?.stopPropagation()}
		>
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
			<div class="buttons-box flex w-full items-center justify-end gap-4">
				<div class="button-box cancel-button">
					<Button
						text="Cancelar"
						width="100%"
						variant="secondary"
						onClick={close}
						disabled={isLoading}
					/>
				</div>

				<div class="button-box register-button">
					<Button
						text="Registrar"
						width="100%"
						onClick={handleRegisterButton}
						loading={isLoading}
					/>
				</div>
			</div>
		</div>
	</Modal>
{/if}

<style>
	.modal-container {
		background: var(--modal-background-color);
		box-shadow: 0 4px 4px 2px rgba(0, 0, 0, 0.5);
	}

	.buttons-box {
		flex-direction: column-reverse;
	}

	.button-box {
		width: 100%;
	}

	@media (min-width: 475px) {
		.buttons-box {
			flex-direction: row;
		}

		.cancel-button {
			width: 130px;
		}

		.register-button {
			width: 170px;
		}
	}
</style>
