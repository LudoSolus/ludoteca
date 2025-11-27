<script lang="ts">
	import Modal from '../atoms/Modal.svelte';

	export let isOpen: boolean;
	export let text: string;
	export let subTitle: string | null = null;
	export let confirmButtonText: string;
	export let onConfirm: () => void;

	function close() {
		isOpen = false;
	}
</script>

{#if isOpen}
	<Modal {onConfirm} onClose={close} isLoading={false} {confirmButtonText}>
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
