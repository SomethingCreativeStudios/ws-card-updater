import * as components from "./components";

const cheerio = require('cheerio');
const axios = require('axios');


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
async function fetchData(url){
  console.log("Crawling data...")
  // make http call to url
  let response = await fetch(url);
  
  
  return response;
}
const mainFunc = async () => {
  
  const baseurl = "https://heartofthecards.com";
  const url = "https://www.heartofthecards.com/code/cardlist.html?pagetype=ws"
  // fetches html data from iban website

  let res = await fetchData(url);

  const html = await res.text();
  

  // mount html page to the root element
  const $ = cheerio.load(html);

  let dataObj = new Object();
  // Below will grab the table with the information
  const allLinks = $('a');

  console.log(allLinks);
  const cardSetLinks = allLinks.filter((index, link) =>{
    //console.log("What are you logging at link and index ", link, index);
    const href = $(link).attr('href') || " ";
    //console.log("What is href now? ", href);
    return href.includes('cardset=');})
  
  console.log("All Card set links " , cardSetLinks);
  cardSetLinks.each(function() {
    let path = this.find("href")
  })
}
export {ComponentLibrary, mainFunc};
