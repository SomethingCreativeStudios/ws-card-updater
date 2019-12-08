import * as components from "./components";

const ComponentLibrary = {
  // @ts-ignore
  install(Vue, options = {}) {
    // components
    for (const componentName in components) {
      // @ts-ignore
      const component = components[componentName];

      Vue.component(component.name, component);
    }
  },
};

export default ComponentLibrary;

// @ts-ignore
if (typeof window !== "undefined" && window.Vue) {
  // @ts-ignore
  window.Vue.use(ComponentLibrary);
}
