<script lang="ts">
	import { generateId } from '$lib/shared/helpers/generate-id';

	export let id: string = generateId(5);
	export let width: string = '100%';
	export let height: string = 'fit-content';
	export let value: string | null = null;
	export let placeholder: string | null = null;
	export let error: string | null = null;
	export let label: string | null = null;
	export let onInput: ((value: string) => void) | undefined = undefined;

	function handleInput(event: any) {
		value = event.target.value;
		if (onInput) {
			onInput(value!);
		}
	}
</script>

<div class="flex flex-col" style="width: {width}; height: {height};">
	<label for={id} class="mb-1 w-full truncate font-bold text-black">
		{label}
	</label>

	<textarea
		{id}
		rows="7"
		cols="30"
		class={`w-full rounded-md border px-3 py-2 focus:ring-2 focus:ring-yellow-400 focus:outline-none ${
			error ? 'border-red-500 focus:ring-red-500' : 'border-black'
		}`}
		{placeholder}
		on:input={handleInput}
		bind:value
	>
	</textarea>

	<span class="mt-1 h-4 text-xs text-red-500">{error}</span>
</div>

<style>
	label,
	textarea {
		font-size: 14px;
		resize: none;
	}

	@media (min-width: 320px) {
		label,
		textarea {
			font-size: 16px;
		}
	}
</style>
