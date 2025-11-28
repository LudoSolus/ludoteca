<script lang="ts">
	import Modal from '../atoms/Modal.svelte';

	export let isOpen: boolean;
	export let text: string;
	export let subTitle: string | null = null;
	export let confirmButtonText: string;
	export let isLoading: boolean = false;
	export let onConfirm: () => void;

	function closeModal() {
		isOpen = false;
	}
</script>

{#if isOpen}
	<Modal {onConfirm} onClose={closeModal} isLoading={isLoading} {confirmButtonText}>
		<div class="flex flex-col gap-2">
			<p class="text-center text-lg font-medium sm:text-xl">
				{text}
			</p>
			{#if subTitle}
				<p class="text-center text-sm font-medium sm:text-lg">
					{subTitle}
				</p>
			{/if}
		</div>
	</Modal>
{/if}
