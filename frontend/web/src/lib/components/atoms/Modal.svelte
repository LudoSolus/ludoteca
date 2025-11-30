<script lang="ts">
	import Button from './Button.svelte';

	export let onClose: () => void;
	export let onConfirm: () => void;
	export let confirmButtonText: string
	export let isLoading: boolean;
</script>

<!-- svelte-ignore a11y_no_static_element_interactions -->
<!-- svelte-ignore a11y_click_events_have_key_events -->
<div
	class="absolute top-0 right-0 bottom-0 left-0 z-11 flex items-center justify-center backdrop-blur-md"
	on:click={() => onClose()}
>
	<div
		class="modal-container flex max-w-9/10 flex-col items-center gap-2 rounded-2xl p-5 sm:gap-8 sm:px-15 sm:py-10 md:max-w-170"
		on:click={() => event?.stopPropagation()}
	>
		<slot />
		<div class="buttons-box flex w-full items-center justify-end gap-4">
			<div class="button-box cancel-button">
				<Button
					text="Cancelar"
					width="100%"
					variant="secondary"
					onClick={onClose}
					disabled={isLoading}
				/>
			</div>

			<div class="button-box register-button">
				<Button text={confirmButtonText} width="100%" onClick={onConfirm} loading={isLoading} />
			</div>
		</div>
	</div>
</div>

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
