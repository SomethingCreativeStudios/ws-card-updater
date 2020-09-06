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

const mainFunc = async () => {
  const url = "https://www.heartofthecards.com/";
  // fetches html data from iban website

  let res = await fetchData(url);

  if(!res.data){
    console.log("Invalid data Obj");
    return;
  }

  const html = res.data;
  
  // mount html page to the root element
  const $ = cheerio.load(html);

  let dataObj = new Object();
  // Below will grab the table with the information
  const allLinks = $('a');
  
  const cardSetLinks = allLinks.filter((link) => link.href.includes('cardset='));
  

}
export default ComponentLibrary;
