import vue from '@vitejs/plugin-vue'
export default {plugins:[vue()],server:{proxy:{"/api":{"target":"http://127.0.0.1:8182","secure":false}}}}
