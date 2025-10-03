<script>
	import Button from '$lib/components/atoms/Button.svelte';
	import { faArrowLeftLong, faArrowRight, faTrash } from '@fortawesome/free-solid-svg-icons';
</script>

<div class="mt-6 flex flex-col items-center gap-4">
	<Button text={'Entrar'} onClick={() => {}} />
	<Button text={'Entrar'} onClick={() => {}} leftIcon={faTrash} />
	<Button text={'Entrar'} onClick={() => {}} rightIcon={faArrowRight} />
</div>

<style>
</style>
