export const categories = ["French","Dutch","German"]
export const stages = ["Draft","In rehearsal","Signed off"]
export type Draft = {title:string;owner:string;category:string;stage:string;context:string;due:string;notes:string}
export type Record = Draft & {id:string}
export type State = {records:Record[];query:string;filter:string;sort:string;page:string;draft:Draft;id:string;loading:boolean;saving:boolean;error:string;success:string}
export const fresh = ():Draft => ({title:'',owner:'',category:categories[0],stage:stages[0],context:'',due:'',notes:''})
export function select(records:Record[],query:string,category:string,sort:string) {
 const needle=query.trim().toLowerCase()
 return records.filter(r=>(!category || r.category===category) &&
   [r.title,r.owner,r.category,r.stage,r.context,r.notes].join(' ').toLowerCase().includes(needle))
   .sort((a,b)=>sort==='title'?a.title.localeCompare(b.title):a.due.localeCompare(b.due)||a.title.localeCompare(b.title))
}
export function toDraft(input:Draft):Draft {
 const {title,owner,context,notes,due,category,stage}=input
 const result={title:title.trim(),owner:owner.trim(),context:context.trim(),notes:notes.trim(),due,category,stage}
 if(!result.title || !result.owner || !result.context || !result.notes || !due) throw new Error('Complete every field before saving.')
 if(!categories.includes(category)||!stages.includes(stage)) throw new Error('Choose an available category and stage.')
 return result
}
const pageFromHash = () => location.hash==='#guide'?'guide':location.hash==='#editor'?'editor':'board'
async function request<T>(endpoint:string,init?:RequestInit):Promise<T>{
 const res=await fetch(endpoint, init)
 if(!res.ok){
  const body=await res.json().catch(()=>({}))
  throw new Error(body.detail || 'Unable to reach the record service. Please retry.')
 }
 return res.json()
}
export class Desk {
 state:State={records:[],query:'',filter:'',sort:'due',page:pageFromHash()==='guide'?'guide':'board',draft:fresh(),id:'',loading:true,saving:false,error:'',success:''}
 private listeners=new Set<(s:State)=>void>()
 subscribe(fn:(s:State)=>void){this.listeners.add(fn);return ()=>{this.listeners.delete(fn)}}
 set(p:Partial<State>){this.state={...this.state,...p};this.listeners.forEach(fn=>fn(this.state))}
 sync=()=>{this.set({page:pageFromHash(),error:''})}
 start(){window.addEventListener('hashchange',this.sync);void this.load();return ()=>window.removeEventListener('hashchange',this.sync)}
 async load(){this.set({loading:true,error:''});try{
  this.set({records:await request<Record[]>('/api/records')})
 }catch{this.set({error:'The record service is unavailable. Try reloading the list.'})}
 finally{this.set({loading:false})}}
 field(key:keyof Draft,ev:Event){this.set({draft:{...this.state.draft,[key]:value(ev)}})}
 edit(row?:Record){this.set({id:row?.id??'',draft:row?{...row}:fresh(),error:'',success:'',page:'editor'});location.hash='editor';requestAnimationFrame(()=>document.querySelector('main h1')?.scrollIntoView({block:'nearest'}))}
 save=async (ev:Event)=>{
  ev.preventDefault()
  if(this.state.saving)return
  this.set({error:'',saving:true})
  try{
   const {id,draft}=this.state
   const saved=await request<Record>('/api/records'+(id?'/'+encodeURIComponent(id):''),{method:id?'PUT':'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify(toDraft(draft))})
   this.set({records:id?this.state.records.map(r=>r.id===id?saved:r):[...this.state.records,saved],page:'board',query:'',filter:'',success:id?'Changes saved.':'Record created.'})
   location.hash='board'
  }catch(err){this.set({error:err instanceof Error?err.message:'Unable to save.'})}
  finally{this.set({saving:false})}
 }
}
export const value=(e:Event)=>(e.target as HTMLInputElement).value
export const formatDate=(date:string)=>new Intl.DateTimeFormat('en-BE',{day:'numeric',month:'short',timeZone:'UTC'}).format(new Date(date+'T00:00:00Z'))
