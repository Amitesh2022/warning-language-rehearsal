import {expect,it} from 'vitest'
import {fresh,select,toDraft,categories,stages} from './desk'
const sample=(id:string,title:string,category:string,due:string)=>({...fresh(),id,title,category,due,owner:'Desk lead'})
it('filters by owner and category and sorts the matching results by deadline',()=>{
 const rows=[sample('1','Late',categories[0],'2027-02-20'),sample('2','Early',categories[0],'2027-02-01'),sample('3','Other',categories[1],'2027-01-01')]
 expect(select(rows,' desk lead ',categories[0],'due').map(r=>r.id)).toEqual(['2','1'])
 expect(select(rows,'missing','','title')).toEqual([])
 expect(select(rows,'',categories[0],'title').map(r=>r.id)).toEqual(['2','1'])
})
it('rejects blank draft fields and trims fields going to the API',()=>{
 expect(()=>toDraft(fresh())).toThrow('Complete every field')
 const input={...fresh(),title:'  Test run ',owner:'Example team',context:'Example',due:'2027-02-10',notes:'Verified'}
 expect(toDraft(input).title).toBe('Test run')
 expect(()=>toDraft({...input,stage:stages[0]+'?'})).toThrow('available')
})
