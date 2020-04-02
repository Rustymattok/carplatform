
let jsonBody = getJson('/CarShop/jsonBody');
let jsonOil = getJson('/CarShop/jsonOil');
let jsonTransmission =  getJson('/CarShop/jsonTransmission');
let bodyList = [];
let transmissionList = [];
let oilList = [];

/**
 * Get Json data - converted from DataBase.
 * @param url - link for json.
 * @returns {any} - json file.
 */
function getJson(url) {

    return JSON.parse($.ajax({
        type: 'GET',
        url: url,
        contentType:'application/json;charset=UTF-8',
        dataType:'json',
        global: false,
        async: false,
        success: function (data) {
            return data;
        }
    }).responseText);
}
/**
 * Create check box - block Body items.
 * @returns {boolean}
 */
function createCheckBoxBody() {
    var divMainBox = document.getElementById("collapse1");
    var divSubBox = document.createElement("div");
    divSubBox.className = "panel-body";
    for (let i = 0; i < jsonBody.length; i++) {
        var divBox = document.createElement("div");
        divBox.className = "checkbox";
        var labelBox = document.createElement("label");
        var inputBox = document.createElement("input");
        inputBox.type = "checkbox";
        inputBox.className = "option-input checkbox";
     //   inputBox.name = "filter[]";
        inputBox.name = "body";
        inputBox.value = jsonBody[i].name;
        var spanBox = document.createElement("span");
        spanBox.className = "ml-10";
        /*
        * Value from servlet
        * */
        spanBox.innerText = jsonBody[i].name;
        labelBox.appendChild(inputBox);
        labelBox.appendChild(spanBox);
        divBox.appendChild(labelBox);
        divSubBox.appendChild(divBox);
    }
    divMainBox.appendChild(divSubBox);
    return false;
}
/**
 * Create check box - block Oil items.
 * @returns {boolean}
 */
function createCheckBoxOil() {
    var divMainBox = document.getElementById("collapse2");
    var divSubBox = document.createElement("div");
    divSubBox.className = "panel-body";
    for (let i = 0; i < jsonOil.length; i++) {
        var divBox = document.createElement("div");
        divBox.className = "checkbox";
        var labelBox = document.createElement("label");
        var inputBox = document.createElement("input");
        inputBox.type = "checkbox";
        inputBox.className = "option-input checkbox";
        inputBox.name = "Oil";
        inputBox.value = jsonOil[i].name;
        var spanBox = document.createElement("span");
        spanBox.className = "ml-10";
        /*
        * Value from servlet
        * */
        spanBox.innerText = jsonOil[i].name;
        labelBox.appendChild(inputBox);
        labelBox.appendChild(spanBox);
        divBox.appendChild(labelBox);
        divSubBox.appendChild(divBox);
    }
    divMainBox.appendChild(divSubBox);
    return false;
}
/**
 * Create check box - block Transmission items.
 * @returns {boolean}
 */
function createCheckBoxTransmission() {
    var divMainBox = document.getElementById("collapse3");
    var divSubBox = document.createElement("div");
    divSubBox.className = "panel-body";
    for (let i = 0; i < jsonTransmission.length; i++) {
        var divBox = document.createElement("div");
        divBox.className = "checkbox";
        var labelBox = document.createElement("label");
        var inputBox = document.createElement("input");
        inputBox.type = "checkbox";
        inputBox.className = "option-input checkbox";
        inputBox.name = "transmission";
        inputBox.value = jsonTransmission[i].name;
        var spanBox = document.createElement("span");
        spanBox.className = "ml-10";
        /*
        * Value from servlet
        * */
        spanBox.innerText = jsonTransmission[i].name;
        labelBox.appendChild(inputBox);
        labelBox.appendChild(spanBox);
        divBox.appendChild(labelBox);
        divSubBox.appendChild(divBox);
    }
    divMainBox.appendChild(divSubBox);
    return false;
}
/**
 * Create block items.
 * @returns {boolean}
 */
function createItemHtml() {
    let json = getJson('/CarShop/json');
    let jsonFilter = getJson('/CarShop/jsonfilter');
    console.log(jsonFilter[0].id);
    if(jsonFilter[0].id !== null){
        json = jsonFilter;
    }
    /*
    * description of main ul. To this block all sub block.
    * */
    var ulFlag = document.getElementById("flag");
    for (let i = 0; i < json.length; i++) {
        /*
        * create div block for item.
        * */
        var divCommon = document.createElement("div");
        divCommon.className = "media align-items-lg-center flex-column flex-lg-row";
        /*
        * create li of item.
        * */
        var liCommon = document.createElement("li");
        liCommon.className = "bodyList-group-item";
        /*
        * create image of item.
        * */
        var img = document.createElement("img");
        img.src = json[i].image;
        img.alt = "Generic placeholder image";
        img.width = "250";
        img.height = "200";
        img.className = "order-1 order-lg-1";
        /*
        * add image to div item block.
        * */
        divCommon.appendChild(img)
        /*
        * create sub div of li block.
        * */
        var div = document.createElement("div");
        div.className = "media-body order-2 order-lg-2 ml-lg-5";
        /*
        * create title h5 of item and add to sub div block.
        * */
        var h5 = document.createElement("h5");
        h5.className = "mt-0 mb-1";
        h5.innerText = "Модель Авто";
        div.appendChild(h5);
        /*
         * create span element for price and add to sub div block.
         */
        var span = document.createElement("span");
        span.className = "product_price price-new";
        span.innerText = json[i].price + " Р";
        div.appendChild(span);
        /*
         * create seperate line
         */
        var separator = document.createElement("hr");
        separator.className = "mb-2 mt-1 seperator";
        div.appendChild(separator);
        /*
         * create sub sub div block.
         */
        var divList = document.createElement("div");
        divList.className = "d-flex align-items-center justify-content-between mt-1";
        /*
        * create ul element.
        * */
        var ulFirst = document.createElement("ul");
        ulFirst.className = "bodyList-inline small";
        /*
        * create li elements of ul add attached.
        * */
        var liFirst1 = document.createElement("li");
        var liFirst2 = document.createElement("li");
        var liFirst3 = document.createElement("li");
        liFirst1.innerText =  json[i].brand;
        liFirst2.innerText = json[i].model;
        liFirst3.innerText = json[i].body;
        ulFirst.appendChild(liFirst1);
        ulFirst.appendChild(liFirst2);
        ulFirst.appendChild(liFirst3);
        /*
        * create second ul element and it's li and attached.
        * */
        var ulSecond = document.createElement("ul")
        ulSecond.className = "bodyList-inline small";
        var liSecond1 = document.createElement("li")
        var liSecond2 = document.createElement("li")
        var liSecond3 = document.createElement("li")
        liSecond1.innerText = json[i].oil;
        liSecond2.innerText = json[i].transmission;
        liSecond3.innerText = json[i].status;
        ulSecond.appendChild(liSecond1);
        ulSecond.appendChild(liSecond2);
        ulSecond.appendChild(liSecond3);
        /*
        * attached all elements step by step.
        * */
        divList.appendChild(ulSecond);
        divList.appendChild(ulFirst);
        div.appendChild(divList);
        divCommon.appendChild(div);
        liCommon.appendChild(divCommon);
        ulFlag.appendChild(liCommon);
    }
    return false;
}

/**
 * Post method for ActiveFilter servlet. Send selected filter.
 * @returns {boolean}
 */
function choosedFilter() {
    var resultBody = JSON.stringify(bodyList);
    var resultOil = JSON.stringify(oilList);
    var resultTransmission = JSON.stringify(transmissionList);
        $.post('/CarShop/jsonfilter', {
            body:resultBody,
            oil:resultOil,
            transmission:resultTransmission
        });
        return false;
}
/**
 * Method by button send activate.
 */
function app() {
    choosedFilter();
    setTimeout(updatePage(),1000);
}
function updatePage() {
    location.replace("/CarShop/");
}
/**
 * Method for work with data selected.
 */
function application() {
    let work = document.getElementById("accordion");
    work.onclick = function(event){
        let bt = event.target.closest("input");
        if(bt.name === "body"){
            bodyList.push(bt.value);
        }
        if(bt.name === "Oil"){
            oilList.push(bt.value);
        }
        if(bt.name === "transmission"){
            transmissionList.push(bt.value);
        }
    }
}


