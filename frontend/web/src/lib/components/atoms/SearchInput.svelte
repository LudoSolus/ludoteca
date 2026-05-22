<script lang="ts">
	import { generateId } from '$lib/shared/helpers/generate-id';

	export let id: string = generateId(5);
	export let width: string = '100%';
	export let height: string = 'fit-content';
	export let value: string | null = null;
	export let placeholder: string | null = null;
	export let label: string | null = null;
	export let onInput: (value: string) => void = () => {};

	function handleInput(event: any) {
		value = event.target.value;
		onInput(value!);
	}
</script>

<div class="flex flex-col" style="width: {width}; height: {height};">
	<label for={id} class="mb-1 font-bold text-black">
		{label}
	</label>

	<div class="flex gap-1 h-full">
		<input
			{id}
			type="text"
			bind:value
			{placeholder}
			on:input={handleInput}
			class={`w-full h-full rounded-md border border-black px-3 py-2 focus:ring-2 focus:ring-yellow-400 focus:outline-none`}
		/>
	</div>
</div>

<style>
	label,
	input {
		font-size: 16px;
	}

	@media (min-width: 320px) {
		label,
		input {
			font-size: 16px;
		}
	}
</style>
