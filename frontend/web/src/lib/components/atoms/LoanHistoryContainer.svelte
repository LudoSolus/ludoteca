<script lang="ts">
	import { formatDate } from '$lib/shared/helpers/format-date';
	import { faCalendar } from '@fortawesome/free-regular-svg-icons';
	import { faUser } from '@fortawesome/free-solid-svg-icons';
	import Fa from 'svelte-fa';

	export let userEmail: string;
	export let loanDate: Date;
	export let onClick: () => void = () => {};
</script>

<button class="flex items-center justify-between min-h-12 min-[480px]:min-h-[10vw] min-[590px]:min-h-14 h-12 min-[480px]:h-[10vw] min-[590px]:h-14 w-[94vw] min-[590px]:w-136 gap-2" on:click={onClick}>
	<div class="type-icon-box flex items-center justify-center" style="background-color: #992C2C;">
		<Fa icon={faUser} color="#fff" size="2x" />
	</div>

	<p class="name-text">
		{userEmail}
	</p>

	<div class="flex gap-2 items-center">
		<Fa icon={faCalendar} />
		<p class="text-sm font-extrabold">
			{formatDate(loanDate, true)}
		</p>
	</div>
</button>

<style>
	button {
		background-color: white;
		border: 2px solid #000;
		border-top-left-radius: 10px;
		border-bottom-left-radius: 10px;
		border-top-right-radius: 50px;
		border-bottom-right-radius: 50px;
		padding-right: 20px;
		cursor: pointer;
	}

	.type-icon-box {
		width: 60px;
		min-width: 60px;
		height: 100%;
	}

	.name-text {
		max-width: 280px;
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
	}
</style>
