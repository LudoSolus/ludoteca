<script lang="ts">
	export let type = 'text';
	export let width: string = '100%';
	export let value: string | null = null;
	export let placeholder: string | null = null;
	export let error: string | null = null;
	export let label: string | null = null;
	export let onInput: (value: string) => void;

	function handleInput(event: any) {
		value = event.target.value;
		onInput(value!);
	}
</script>

<div class="flex w-full flex-col" style="width: {width}; height: 100%;">
	<label for="input" class="mb-1 font-bold text-black">
		{label}
	</label>

	<input
		id="input"
		{type}
		bind:value
		{placeholder}
		on:input={handleInput}
		class={`w-full rounded-md border px-3 py-2 focus:ring-2 focus:ring-yellow-400 focus:outline-none ${
			error ? 'border-red-500 focus:ring-red-500' : 'border-black'
		}`}
	/>

	{#if error}
		<span class="mt-1 text-xs text-red-500">{error}</span>
	{/if}
</div>
