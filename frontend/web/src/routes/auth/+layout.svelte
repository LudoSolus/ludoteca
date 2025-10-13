<script lang="ts">
	import defaultImg from '$lib/assets/default.png';

	let { children } = $props();
</script>

<div class="flex h-full items-center justify-between gap-10 overflow-hidden ps-10">
	<div class="flex h-full w-full items-center justify-center">
		<img src={defaultImg} alt="Imagem padrão" />
	</div>
	{@render children?.()}
</div>

<style>
	img {
		width: 100%;
		max-width: 500px;
		height: auto;
		object-fit: contain;
	}
</style>
