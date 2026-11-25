<script setup lang="ts">
import {computed,shallowRef,onMounted,onUnmounted} from 'vue'
import {Desk,select,categories,stages,value,formatDate,type State} from './desk'
const desk=new Desk()
const s=shallowRef(desk.state)
const off=desk.subscribe(state=>{s.value=state})
let stop:(()=>void)|undefined
onMounted(()=>{stop=desk.start()})
onUnmounted(()=>{off();stop?.()})
const count=(state:State)=>Object.fromEntries(stages.map(stage=>[stage,state.records.filter(r=>r.stage===stage).length]))
const visible=computed(()=>select(s.value.records,s.value.query,s.value.filter,s.value.sort))
const totals=computed(()=>count(s.value))
</script>
<template>
<header class="top"><div class="wrap bar"><a class="brand" href="#board"><span class="mark" aria-hidden="true">◫</span>Warning Language Rehearsal</a><nav aria-label="Workspace"><a href="#board" :aria-current="s.page==='board' ? 'page' : undefined">Work queue</a><a href="#guide" :aria-current="s.page==='guide' ? 'page' : undefined">Playbook</a></nav></div></header>
<main class="wrap">
<template v-if="s.page==='board'">
<section class="hero"><div><p class="eyebrow">PUBLIC COMMUNICATION / WORK QUEUE</p><h1>Warning Language Rehearsal</h1><p class="lead">Rehearse accessible multilingual municipal messages before an exercise begins.</p></div><button type="button" @click="desk.edit()">New rehearsal</button></section>
<section class="stats" aria-label="Queue by stage"><template v-for="stage in stages" :key="stage"><div class="stat"><span class="stat-label">{{ stage }}</span><strong>{{ totals[stage] ?? 0 }}</strong></div></template></section>
<section class="panel" aria-labelledby="queue"><div class="panel-head"><h2 id="queue">Rehearsal queue</h2><span>{{ visible.length }} visible</span></div>
<div class="filters"><label>Search records<input type="search" placeholder="Title, owner or context" :value="s.query" @input="desk.set({query:value($event)})" /></label><label>Language<select aria-label="Language" :value="s.filter" @change="desk.set({filter:value($event)})"><option value="">All categories</option><template v-for="x in categories" :key="x"><option :value="x">{{ x }}</option></template></select></label><label>Sort by<select aria-label="Sort by" :value="s.sort" @change="desk.set({sort:value($event)})"><option value="due">Due date</option><option value="title">Title</option></select></label></div>
<template v-if="s.success"><p role="status" class="success">{{ s.success }}</p></template>
<template v-if="s.error"><p role="alert">{{ s.error }}</p><button type="button" @click="desk.load()">Reload list</button></template>
<template v-if="s.loading"><p role="status">Loading records…</p></template>
<template v-if="!s.loading && !s.error && visible.length===0"><p class="empty">No records match. Change the search or category.</p></template>
<ul class="records"><template v-for="row in visible" :key="row.id"><li><div><strong>{{ row.title }}</strong><small>{{ row.owner }} · {{ row.context }}</small></div><span class="pill">{{ row.category }}</span><span>{{ row.stage }}</span><span>{{ formatDate(row.due) }}</span><button type="button" class="secondary" @click="desk.edit(row)" :aria-label="'Edit '+row.title">Edit</button></li></template></ul></section></template>
<template v-if="s.page==='editor'"><div class="inner"><a href="#board">← Back to work queue</a><p class="eyebrow">PUBLIC COMMUNICATION / RECORD</p><h1>{{ s.id?'Edit record':'New record' }}</h1><form class="panel form" @submit="desk.save($event)">
<label>Rehearsal title<input required type="text" maxLength="100" :value="s.draft.title" @input="desk.field('title',$event)" /></label><label>Language reviewer<input required type="text" maxLength="80" :value="s.draft.owner" @input="desk.field('owner',$event)" /></label><label>Language<select aria-label="Language" :value="s.draft.category" @change="desk.field('category',$event)"><template v-for="x in categories" :key="x"><option :value="x">{{ x }}</option></template></select></label><label>Stage<select aria-label="Stage" :value="s.draft.stage" @change="desk.field('stage',$event)"><template v-for="x in stages" :key="x"><option :value="x">{{ x }}</option></template></select></label><label>Exercise channel<input required type="text" maxLength="100" :value="s.draft.context" @input="desk.field('context',$event)" /></label><label>Due date<input required type="date"  :value="s.draft.due" @input="desk.field('due',$event)" /></label>
<label class="wide">Rehearsal notes<textarea required maxLength="500" rows="3" :value="s.draft.notes" @input="desk.field('notes',$event)"></textarea></label>
<template v-if="s.error"><p role="alert" class="wide">{{ s.error }}</p></template>
<div class="wide form-actions"><button type="submit" :disabled="s.saving">{{ s.saving?'Saving…':'Save record' }}</button><a href="#board">Cancel</a></div></form></div></template>
<template v-if="s.page==='guide'"><div class="inner"><p class="eyebrow">PUBLIC COMMUNICATION / PLAYBOOK</p><h1>Rehearse before sending</h1><article class="panel guide"><h2>Review checklist</h2><p>Use fictional messages only. Confirm that each version gives the same action and can be understood without relying on a map or sound.</p><h2>Before closing a record</h2><p>Give the next team a named owner, a clear deadline and a short note explaining the decision. Use sample information in this workspace.</p><a href="https://interoperable-europe.ec.europa.eu/collection/rolling-plan-ict-standardisation/emergency-communications-and-public-warning-systems-rp-2026">Read the official background</a></article></div></template>
</main><footer class="wrap footer">Warning Language Rehearsal <span>·</span> Sample workspace</footer></template>
