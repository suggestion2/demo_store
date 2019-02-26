$(function () {

    window.jsonParser = new JsonParser("");

    /*$("#secretKey").val($("option:selected", $("#selClient")).attr("secretKey"));

    $("#selClient").change(function(){
        var optionSelected = $("option:selected", this);
        $("#secretKey").val($(optionSelected).attr("secretKey"));

        $("#p_clientId").val( $("#selClient").val());
        $("#p_secretKey").val( $("#secretKey").val());
    });

    $("#p_clientId").val( $("#selClient").val());
    $("#p_secretKey").val( $("#secretKey").val());*/

});

function openTab(tab) {
    if (tab === 1) {
        $("#tabs-2").hide();
        $("#tabs-1").show();
    } else if (tab === 2) {
        $("#tabs-1").hide();
        $("#tabs-2").show();
    }
}

function uuid() {
    var s = [];
    var hexDigits = "0123456789abcdef";
    for (var i = 0; i < 36; i++) {
        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
    }
    s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
    s[8] = s[13] = s[18] = s[23] = "-";

    var uuid = s.join("");
    return uuid;
}

function showResult(settings) {
    $.fn.mask();

    $("#requestedUrl").val($("#requestedUrl").attr("domain") + settings.url);
    $("#requestedMethod").text(settings.type);
    $("#p_data").text(settings.data == null ? "" : settings.data);

    var timestamp = Math.round(new Date().getTime());
    /*var requestId = uuid();
    var visitorId = $("#visitorId").val();*/

    /*var signature = [];
    signature.push("uri=" + settings.url);
    signature.push("&method=" + settings.type.toUpperCase());
    if (settings.data != null && settings.data.length > 0)
        signature.push("&body=" + settings.data);
    signature.push("&timestamp=" + timestamp);
    signature.push("&requestId=" + requestId);
    if(visitorId != null && visitorId.length > 0)
        signature.push("&visitorId=" + visitorId);
    console.log(signature.join(''));
    var signatureStr =  CryptoJS.HmacSHA1(signature.join(''),$("#secretKey").val()).toString(CryptoJS.enc.Base64);
    console.log(signatureStr);*/

    $.ajax({
        type: settings.type,
        url: settings.url,
        dataType: settings.dataType,
        data: settings.data,
        contentType: settings.contentType === null ? settings.contentType : "application/json",
        processData: settings.processData === null ? settings.processData : true,
        async: settings.async === null ? settings.async : true,
        headers: {
            "Timestamp": timestamp
            /*,"Client-Id": $("#selClient").val(),
            "Visitor-Id" : $("#visitorId").val(),
            "Request-Id" : requestId,
            "Client-Signature": signatureStr*/
        },
        success: function (data) {

            window.jsonParser.jsonContent = JSON.stringify(data);
            window.jsonParser.init();

            if (settings.success)
                settings.success(data);

            $.fn.mask.close();
        },
        error: function (data) {
            window.jsonParser.jsonContent = JSON.stringify(data);
            window.jsonParser.init();
            $.fn.mask.close();
        }
    });
}

var commonModule = {
    login: function () {
        var settings = {
            type: "POST",
            url: "/management/user/login",
            dataType: "json",
            data: JSON.stringify({
                "name": $("#login-m-name").val(),
                "password": $("#login-m-password").val()
            })
        };
        showResult(settings);
    },
    resetPwd: function () {
        var settings = {
            type: "PUT",
            url: "/management/user/resetPassword",
            dataType: "json",
            data: JSON.stringify({
                "originPassword": $("#resetPwd-m-originPwd").val(),
                "newPassword": $("#resetPwd-m-newPwd").val()
            })
        };
        showResult(settings);
    },
    currentUser: function () {
        var settings = {
            type: "GET",
            url: "/management/user/current",
            dataType: "json"
        };
        showResult(settings);
    },
    logout: function () {
        var settings = {
            type: "GET",
            url: "/management/user/logout",
            dataType: "json"
        };
        showResult(settings);
    }

};
var customerModule = {
    captcha: function () {
        var settings = {
            type: "POST",
            url: "/api/captcha",
            dataType: "json",
            data: JSON.stringify({
                "phone": $("#customer-captcha-phone").val()
            })
        };
        showResult(settings);
    },
    create: function () {
        var settings = {
            type: "POST",
            url: "/api/register",
            dataType: "json",
            data: JSON.stringify({
                "phone": $("#customer-create-phone").val(),
                "captcha": $("#customer-create-captcha").val(),
                "password": $("#customer-create-loginPassword").val()
            })
        };
        showResult(settings);
    },
    login: function () {
        var settings = {
            type: "POST",
            url: "/api/login",
            dataType: "json",
            data: JSON.stringify({
                "phone": $("#customer-a-login-phone").val(),
                "password": $("#customer-a-login-loginPassword").val()
            })
        };
        showResult(settings);
    },
    resetLogPwd: function () {
        var settings = {
            type: "PUT",
            url: "/api/customer/resetPassword",
            dataType: "json",
            data: JSON.stringify({
                "originPassword": $("#customer-resetLogPwd-a-originPwd").val(),
                "newPassword": $("#customer-resetLogPwd-a-newPwd").val()
            })
        };
        showResult(settings);
    },
    current: function () {
        var settings = {
            type: "GET",
            url: "/api/current",
            dataType: "json"
        };
        showResult(settings);
    },
    update: function () {
        var settings = {
            type: "PUT",
            url: "/api/customer/update",
            dataType: "json",
            data: JSON.stringify({
                "name": $("#customer-update-a-name").val()
            })
        };
        showResult(settings);
    },
    logout: function () {
        var settings = {
            type: "GET",
            url: "/api/logout",
            dataType: "json"
        };
        showResult(settings);
    }
};
var customerAddressModule = {
    list: function () {
        var settings = {
            type: "GET",
            url: "/api/customerAddress/list",
            dataType: "json"
        };
        showResult(settings);
    },
    detail: function () {
        var settings = {
            type: "GET",
            url: "/api/customerAddress/" + $("#address-detail-id").val(),
            dataType: "json"
        };
        showResult(settings);
    },
    create: function () {
        var settings = {
            type: "POST",
            url: "/api/customerAddress/create",
            dataType: "json",
            data: JSON.stringify({
                "contact": $("#address-create-contact").val(),
                "contactPhone": $("#address-create-phone").val(),
                "province": $("#address-create-province").val(),
                "city": $("#address-create-city").val(),
                "county": $("#address-create-county").val(),
                "address": $("#address-create-address").val(),
                "primary": $("#address-create-primary").val()
            })
        };
        showResult(settings);
    },
    update: function () {
        var settings = {
            type: "PUT",
            url: "/api/customerAddress/update",
            dataType: "json",
            data: JSON.stringify({
                "id": $("#address-update-id").val(),
                "contact": $("#address-update-contact").val(),
                "contactPhone": $("#address-update-phone").val(),
                "province": $("#address-update-province").val(),
                "city": $("#address-update-city").val(),
                "county": $("#address-update-county").val(),
                "address": $("#address-update-address").val(),
                "primary": $("#address-update-primary").val()
            })
        };
        showResult(settings);
    },
    delete: function () {
        var settings = {
            type: "DELETE",
            url: "/api/customerAddress/delete/" + $("#address-delete-id").val(),
            dataType: "json"
        };
        showResult(settings);
    }
};


var systemParamsModule= {
    list: function () {
        var settings = {
            type: "GET",
            url: "/management/systemParams/list",
            dataType: "json"
        };
        showResult(settings);
    },
    update: function () {
        var settings = {
            type: "PUT",
            url: "/management/systemParams/update",
            dataType: "json",
            data: JSON.stringify({
                "id": $("#systemParam-update-id").val(),
                "value": $("#systemParam-update-value").val()
            })
        };
        showResult(settings);
    },
};

var goodsCategoryModule= {
    list: function () {
        var settings = {
            type: "GET",
            url: "/management/goodsCategory/list",
            dataType: "json"
        };
        showResult(settings);
    },
    listApi: function () {
        var settings = {
            type: "GET",
            url: "/api/goods/category",
            dataType: "json"
        };
        showResult(settings);
    },
    create: function () {
        var settings = {
            type: "POST",
            url: "/management/goodsCategory/create",
            dataType: "json",
            data: JSON.stringify({
                "name": $("#goodsCategory-create-name").val(),
                "level": $("#goodsCategory-create-level").val(),
                "parentId": $("#goodsCategory-create-parentId").val()
            })
        };
        showResult(settings);
    },
    update: function () {
        var settings = {
            type: "PUT",
            url: "/management/goodsCategory/update",
            dataType: "json",
            data: JSON.stringify({
                "id": $("#goodsCategory-update-id").val(),
                "name": $("#goodsCategory-update-name").val(),
                "level": $("#goodsCategory-update-level").val(),
                "parentId": $("#goodsCategory-update-parentId").val()
            })
        };
        showResult(settings);
    },
    updateStatus: function () {
        var settings = {
            type: "PUT",
            url: "/management/goodsCategory/resetStatus",
            dataType: "json",
            data: JSON.stringify({
                "id": $("#goodsCategory-status-id").val(),
                "status": $("#goodsCategory-status-status").val()
            })
        };
        showResult(settings);
    },
    deleteById: function () {
        var settings = {
            type: "DELETE",
            url: "/management/goodsCategory/delete/" + $("#goodsCategory-delete-id").val(),
            dataType: "json"
        };
        showResult(settings);
    }
};

var goodsModule= {
    list: function () {
        var settings = {
            type: "POST",
            url: "/management/goods/list",
            dataType: "json",
            data: JSON.stringify({
                "content": $("#goods-list-content").val(),
                "categoryId1": $("#goods-list-categoryId1").val(),
                "status": $("#goods-list-status").val(),
                "pageIndex": $("#goods-list-pageIndex").val(),
                "pageSize": $("#goods-list-pageSize").val()
            })
        };
        showResult(settings);
    },
    listApi: function () {
        var settings = {
            type: "POST",
            url: "/api/goods/list",
            dataType: "json",
            data: JSON.stringify({
                "content": $("#goods-api-list-content").val(),
                "categoryId1": $("#goods-api-list-categoryId1").val(),
                "categoryId2": $("#goods-api-list-categoryId2").val(),
                "categoryId3": $("#goods-api-list-categoryId3").val(),
                "pageIndex": $("#goods-api-list-pageIndex").val(),
                "pageSize": $("#goods-api-list-pageSize").val()
            })
        };
        showResult(settings);
    },
    detail: function () {
        var settings = {
            type: "GET",
            url: "/management/goods/" + $("#goods-detail-id").val(),
            dataType: "json"
        };
        showResult(settings);
    },
    detailApi: function () {
        var settings = {
            type: "GET",
            url: "/api/goods/" + $("#goods-api-detail-id").val(),
            dataType: "json"
        };
        showResult(settings);
    },
    imageApi: function () {
        var url = window.location.href;
        var arr = url.split("/");
        var result = arr[0] + "//" + arr[2];
        window.open(
            result +"/api/goods/image/" + $("#goods-api-image-fileName").val(),
            '_blank' // <- This is what makes it open in a new window.
        );
        /*var settings = {
            type: "GET",
            url: ,
            dataType: "json"
        };
        showResult(settings);*/
    },
    create: function () {
        var settings = {
            type: "POST",
            url: "/management/goods/create",
            dataType: "json",
            data: JSON.stringify({
                "name": $("#goods-create-name").val(),
                "categoryId1": $("#goods-create-categoryId1").val(),
                "categoryId2": $("#goods-create-categoryId2").val(),
                "category1": $("#goods-create-category1").val(),
                "category2": $("#goods-create-category2").val(),
                "bannerUrl": $("#goods-create-bannerUrl").val(),
                "imagesUrl": $("#goods-create-imagesUrl").val(),
                "remarks": $("#goods-create-remarks").val(),
                "specList":[
                    {
                        "form":{
                            "tempId": $("#goods-create-sku1-tempId").val(),
                            "title": $("#goods-create-sku1-title").val()
                        },
                        "subList":[
                            {
                                "tempId": $("#goods-create-sku11-tempId").val(),
                                "tempParentId": $("#goods-create-sku11-parentTempId").val(),
                                "title": $("#goods-create-sku11-title").val(),
                                "primary": $("#goods-create-sku11-primary").is(':checked') ? 1 : 0
                            },
                            {
                                "tempId": $("#goods-create-sku12-tempId").val(),
                                "tempParentId": $("#goods-create-sku12-parentTempId").val(),
                                "title": $("#goods-create-sku12-title").val(),
                                "primary": $("#goods-create-sku12-primary").is(':checked') ? 1 : 0
                            }
                        ]
                    },
                    {
                        "form":{
                            "tempId": $("#goods-create-sku2-tempId").val(),
                            "title": $("#goods-create-sku2-title").val()
                        },
                        "subList":[
                            {
                                "tempId": $("#goods-create-sku21-tempId").val(),
                                "tempParentId": $("#goods-create-sku21-parentTempId").val(),
                                "title": $("#goods-create-sku21-title").val(),
                                "primary": $("#goods-create-sku21-primary").is(':checked') ? 1 : 0
                            },
                            {
                                "tempId": $("#goods-create-sku22-tempId").val(),
                                "tempParentId": $("#goods-create-sku22-parentTempId").val(),
                                "title": $("#goods-create-sku22-title").val(),
                                "primary": $("#goods-create-sku22-primary").is(':checked') ? 1 : 0
                            }
                        ]
                    }
                ],
                "unitList":[
                    {
                        "specIds": $("#goods-create-unit1-specIds").val(),
                        "title": $("#goods-create-unit1-title").val(),
                        "stocks": $("#goods-create-unit1-stocks").val(),
                        "price": $("#goods-create-unit1-price").val(),
                        "shippingCost": $("#goods-create-unit1-shippingCost").val(),
                        "salesVolume": $("#goods-create-unit1-salesVolume").val(),
                        "imageUrl": $("#goods-create-unit1-imageUrl").val(),
                        "primary": $("#goods-create-unit1-primary").is(':checked') ? 1 : 0
                    },
                    {
                        "specIds": $("#goods-create-unit2-specIds").val(),
                        "title": $("#goods-create-unit2-title").val(),
                        "stocks": $("#goods-create-unit2-stocks").val(),
                        "price": $("#goods-create-unit2-price").val(),
                        "shippingCost": $("#goods-create-unit2-shippingCost").val(),
                        "salesVolume": $("#goods-create-unit2-salesVolume").val(),
                        "imageUrl": $("#goods-create-unit2-imageUrl").val(),
                        "primary": $("#goods-create-unit2-primary").is(':checked') ? 1 : 0
                    },
                    {
                        "specIds": $("#goods-create-unit3-specIds").val(),
                        "title": $("#goods-create-unit3-title").val(),
                        "stocks": $("#goods-create-unit3-stocks").val(),
                        "price": $("#goods-create-unit3-price").val(),
                        "shippingCost": $("#goods-create-unit3-shippingCost").val(),
                        "salesVolume": $("#goods-create-unit3-salesVolume").val(),
                        "imageUrl": $("#goods-create-unit3-imageUrl").val(),
                        "primary": $("#goods-create-unit3-primary").is(':checked') ? 1 : 0
                    },
                    {
                        "specIds": $("#goods-create-unit4-specIds").val(),
                        "title": $("#goods-create-unit4-title").val(),
                        "stocks": $("#goods-create-unit4-stocks").val(),
                        "price": $("#goods-create-unit4-price").val(),
                        "shippingCost": $("#goods-create-unit4-shippingCost").val(),
                        "salesVolume": $("#goods-create-unit4-salesVolume").val(),
                        "imageUrl": $("#goods-create-unit4-imageUrl").val(),
                        "primary": $("#goods-create-unit4-primary").is(':checked') ? 1 : 0
                    }
                ]
            })
        };
        showResult(settings);
    },
    update: function () {
        var settings = {
            type: "PUT",
            url: "/management/goods/update",
            dataType: "json",
            data: JSON.stringify({
                "id": $("#goods-update-id").val(),
                "name": $("#goods-update-name").val(),
                "categoryId1": $("#goods-update-categoryId1").val(),
                "categoryId2": $("#goods-update-categoryId2").val(),
                "category1": $("#goods-update-category1").val(),
                "category2": $("#goods-update-category2").val(),
                "bannerUrl": $("#goods-update-bannerUrl").val(),
                "imagesUrl": $("#goods-update-imagesUrl").val(),
                "remarks": $("#goods-update-remarks").val(),
                "specList":[
                    {
                        "form":{
                            "id": $("#goods-update-sku1-id").val(),
                            "title": $("#goods-update-sku1-title").val()
                        },
                        "subList":[
                            {
                                "id": $("#goods-update-sku11-id").val(),
                                "title": $("#goods-update-sku11-title").val(),
                                "primary": $("#goods-update-sku11-primary").is(':checked') ? 1 : 0
                            },
                            {
                                "id": $("#goods-update-sku12-id").val(),
                                "title": $("#goods-update-sku12-title").val(),
                                "primary": $("#goods-update-sku12-primary").is(':checked') ? 1 : 0
                            }
                        ]
                    },
                    {
                        "form":{
                            "id": $("#goods-update-sku2-id").val(),
                            "title": $("#goods-update-sku2-title").val()
                        },
                        "subList":[
                            {
                                "id": $("#goods-update-sku21-id").val(),
                                "title": $("#goods-update-sku21-title").val(),
                                "primary": $("#goods-update-sku21-primary").is(':checked') ? 1 : 0
                            },
                            {
                                "tempId": $("#goods-update-sku22-tempId").val(),
                                "parentId": $("#goods-update-sku22-parentId").val(),
                                "title": $("#goods-update-sku22-title").val(),
                                "primary": $("#goods-update-sku22-primary").is(':checked') ? 1 : 0
                            }
                        ]
                    }
                ],

                "unitList":[
                    {
                        "id": $("#goods-update-unit1-id").val(),
                        "title": $("#goods-update-unit1-title").val(),
                        "stocks": $("#goods-update-unit1-stocks").val(),
                        "price": $("#goods-update-unit1-price").val(),
                        "shippingCost": $("#goods-update-unit1-shippingCost").val(),
                        "salesVolume": $("#goods-update-unit1-salesVolume").val(),
                        "imageUrl": $("#goods-update-unit1-imageUrl").val(),
                        "primary": $("#goods-update-unit1-primary").is(':checked') ? 1 : 0
                    },
                    {
                        "specIds": $("#goods-update-unit2-specIds").val(),
                        "title": $("#goods-update-unit2-title").val(),
                        "stocks": $("#goods-update-unit2-stocks").val(),
                        "price": $("#goods-update-unit2-price").val(),
                        "shippingCost": $("#goods-update-unit2-shippingCost").val(),
                        "salesVolume": $("#goods-update-unit2-salesVolume").val(),
                        "imageUrl": $("#goods-update-unit2-imageUrl").val(),
                        "primary": $("#goods-update-unit2-primary").is(':checked') ? 1 : 0
                    },
                    {
                        "id": $("#goods-update-unit3-id").val(),
                        "title": $("#goods-update-unit3-title").val(),
                        "stocks": $("#goods-update-unit3-stocks").val(),
                        "price": $("#goods-update-unit3-price").val(),
                        "shippingCost": $("#goods-update-unit3-shippingCost").val(),
                        "salesVolume": $("#goods-update-unit3-salesVolume").val(),
                        "imageUrl": $("#goods-update-unit3-imageUrl").val(),
                        "primary": $("#goods-update-unit3-primary").is(':checked') ? 1 : 0
                    },
                    {
                        "specIds": $("#goods-update-unit4-specIds").val(),
                        "title": $("#goods-update-unit4-title").val(),
                        "stocks": $("#goods-update-unit4-stocks").val(),
                        "price": $("#goods-update-unit4-price").val(),
                        "shippingCost": $("#goods-update-unit4-shippingCost").val(),
                        "salesVolume": $("#goods-update-unit4-salesVolume").val(),
                        "imageUrl": $("#goods-update-unit4-imageUrl").val(),
                        "primary": $("#goods-update-unit4-primary").is(':checked') ? 1 : 0
                    }
                ]
            })
        };
        showResult(settings);
    },
    updateStatus: function () {
        var settings = {
            type: "PUT",
            url: "/management/goods/resetStatus",
            dataType: "json",
            data: JSON.stringify({
                "id": $("#goods-status-id").val(),
                "status": $("#goods-status-status").val()
            })
        };
        showResult(settings);
    },
    deleteById: function () {
        var settings = {
            type: "DELETE",
            url: "/management/goods/delete/" + $("#goods-delete-id").val(),
            dataType: "json"
        };
        showResult(settings);
    },
};
var cartModule = {
    info: function () {
        var settings = {
            type: "GET",
            url: "/api/cart/info",
            dataType: "json"
        };
        showResult(settings);
    },
    detail: function () {
        var settings = {
            type: "GET",
            url: "/api/cart/current",
            dataType: "json"
        };
        showResult(settings);
    },
    add: function () {
        var settings = {
            type: "POST",
            url: "/api/cart/add",
            dataType: "json",
            data: JSON.stringify({
                "id": $("#cart-add-goodsId").val(),
                "unitId": $("#cart-add-unitId").val(),
                "count": $("#cart-add-count").val()
            })
        };
        showResult(settings);
    },
    edit: function () {
        var settings = {
            type: "PUT",
            url: "/api/cart/edit",
            dataType: "json",
            data: JSON.stringify({
                "id": $("#cart-edit-itemId").val(),
                "count": $("#cart-edit-count").val()
            })
        };
        showResult(settings);
    },
    deleteById: function () {
        var settings = {
            type: "DELETE",
            url: "/api/cart/delete/" + $("#cart-delete-id").val(),
            dataType: "json"
        };
        showResult(settings);
    },
    checkout: function () {
        var settings = {
            type: "GET",
            url: "/api/cart/checkout",
            dataType: "json"
        };
        showResult(settings);
    }
};
var orderModule= {
    list: function () {
        var settings = {
            type: "POST",
            url: "/management/order/list",
            dataType: "json",
            data: JSON.stringify({
                "content": $("#order-list-content").val(),
                "status": $("#order-list-status").val(),
                "pageIndex": $("#order-list-pageIndex").val(),
                "pageSize": $("#order-list-pageSize").val()
            })
        };
        showResult(settings);
    },
    listApi: function () {
        var settings = {
            type: "POST",
            url: "/api/order/list",
            dataType: "json",
            data: JSON.stringify({
                "content": $("#order-api-list-content").val(),
                "status": $("#order-api-list-status").val(),
                "pageIndex": $("#order-api-list-pageIndex").val(),
                "pageSize": $("#order-api-list-pageSize").val()
            })
        };
        showResult(settings);
    },
    detail: function () {
        var settings = {
            type: "GET",
            url: "/management/order/" + $("#order-detail-id").val(),
            dataType: "json"
        };
        showResult(settings);
    },
    detailApi: function () {
        var settings = {
            type: "GET",
            url: "/api/order/" + $("#order-api-detail-id").val(),
            dataType: "json"
        };
        showResult(settings);
    },
    createApi: function () {
        var jsonObject = {
            "amount": $("#order-api-create-amount").val(),
            "recharge": $("#order-api-create-recharge").val(),
            "remark": $("#order-api-create-remark").val(),
            "list":[]
        };
        if($("#order-api-create-hasItem").val() == '1'){
            jsonObject.list.push({
                "goodsId": $("#order-api-create-goodsId").val(),
                "goodsName": $("#order-api-create-goodsName").val(),
                "price": $("#order-api-create-price").val(),
                "count": $("#order-api-create-count").val()
            })
        }
        var settings = {
            type: "POST",
            url: "/api/order/create",
            dataType: "json",
            data: JSON.stringify(jsonObject)
        };
        showResult(settings);
    },
    updateStatus: function () {
        var settings = {
            type: "PUT",
            url: "/management/order/resetStatus",
            dataType: "json",
            data: JSON.stringify({
                "id": $("#order-status-id").val(),
                "status": $("#order-status-status").val(),
                "cancelReason": $("#order-status-cancelReason").val(),
                "remarks": $("#order-status-remarks").val()
            })
        };
        showResult(settings);
    },
    paid: function () {
        var settings = {
            type: "PUT",
            url: "/api/order/paid",
            dataType: "json",
            data: JSON.stringify({
                "id": $("#order-api-paid-id").val()
            })
        };
        showResult(settings);
    }
};

var customerMaModule= {
    list: function () {
        var settings = {
            type: "POST",
            url: "/management/customer/list",
            dataType: "json" ,
            data: JSON.stringify({
                "content": $("#customer-m-list-content").val(),
                "pageIndex": $("#customer-m-List-startIndex").val(),
                "pageSize": $("#customer-m-List-pageSize").val()
            })
        };
        showResult(settings);
    },
    detail: function () {
        var settings = {
            type: "GET",
            url: "/management/customer/" + $("#customer-m-detail-id").val(),
            dataType: "json"
        };
        showResult(settings);
    },
    resetLoginPasswordById: function () {
        var settings = {
            type: "PUT",
            url: "/management/customer/loginPassword",
            dataType: "json",
            data: JSON.stringify({
                "id": $("#customer-m-resetLoginPassword-id").val()
            })
        };
        showResult(settings);
    },
    resetPaymentPassword: function () {
        var settings = {
            type: "PUT",
            url: "/management/customer/paymentPassword",
            dataType: "json",
            data: JSON.stringify({
                "id": $("#customer-m-resetPaymentPassword-id").val()
            })
        };
        showResult(settings);
    },
    resetStatus: function () {
        var settings = {
            type: "PUT",
            url: "/management/customer/resetBusinessStatus",
            dataType: "json",
            data: JSON.stringify({
                "id": $("#customer-m-status-id").val(),
                "businessStatus": $("#customer-m-status-businessStatus").val()
            })
        };
        showResult(settings);
    },
    resetBusiness: function () {
        var settings = {
            type: "PUT",
            url: "/management/customer/resetBusiness",
            dataType: "json",
            data: JSON.stringify({
                "id": $("#customer-m-business-id").val(),
                "businessName": $("#customer-m-business-businessName").val()
            })
        };
        showResult(settings);
    },
    businessVerify: function () {
        var settings = {
            type: "PUT",
            url: "/management/customer/verifyBusiness",
            dataType: "json",
            data: JSON.stringify({
                "id": $("#customer-m-businessVerify-id").val(),
                "status": $("#customer-m-businessVerify-status").val()
            })
        };
        showResult(settings);
    },
    resetLevel: function () {
        var settings = {
            type: "PUT",
            url: "/management/customer/resetLevel",
            dataType: "json",
            data: JSON.stringify({
                "id": $("#customer-m-level-id").val(),
                "level": $("#customer-m-level-level").val()
            })
        };
        showResult(settings);
    },
    customerParent: function () {
        var settings = {
            type: "GET",
            url: "/management/customer/customerParent/"+ $("#customer-m-customerParent-id").val(),
            dataType: "json"
        };
        showResult(settings);
    },
    customerLowList: function () {
        var settings = {
            type: "POST",
            url: "/management/customer/customerLowList",
            dataType: "json",
            data: JSON.stringify({
                "id": $("#customer-m-customerLowList-id").val(),
                "pageIndex": $("#customer-m-customerLowList-startIndex").val(),
                "pageSize": $("#customer-m-customerLowList-pageSize").val()
            })
        };
        showResult(settings);
    },
    addReduceGold: function () {
        var settings = {
            type: "POST",
            url: "/management/goldRecord/customerAddReduceGold",
            dataType: "json",
            data: JSON.stringify({
                "id": $("#customer-m-addReduceGold-id").val(),
                "type": $("#customer-m-addReduceGold-type").val(),
                "amount": $("#customer-m-addReduceGold-amount").val()
            })
        };
        showResult(settings);
    },
    businessAddReduceGold: function () {
        var settings = {
            type: "POST",
            url: "management/goldRecord/businessAddGoldRecord",
            dataType: "json",
            data: JSON.stringify({
                "id": $("#business-m-addReduceGold-id").val(),
                "type": $("#business-m-addReduceGold-type").val(),
                "amount": $("#business-m-addReduceGold-amount").val()
            })
        };
        showResult(settings);
    },
    addReduceOilDrill: function () {
        var settings = {
            type: "POST",
            url: "management/oilDrillRecord/customerAddReduceOil",
            dataType: "json",
            data: JSON.stringify({
                "id": $("#customer-m-addReduceOilDrill-id").val(),
                "type": $("#customer-m-addReduceOilDrill-type").val(),
                "amount": $("#customer-m-addReduceOilDrill-amount").val()
            })
        };
        showResult(settings);
    },
    businessAddReduceOil: function () {
        var settings = {
            type: "POST",
            url: "management/oilDrillRecord/businessAddGoldOil",
            dataType: "json",
            data: JSON.stringify({
                "id": $("#business-m-businessAddReduceOil-id").val(),
                "type": $("#business-m-businessAddReduceOil-type").val(),
                "amount": $("#business-m-businessAddReduceOil-amount").val()
            })
        };
        showResult(settings);
    },
    becomeVip: function () {
        var settings = {
            type: "put",
            url: "/management/customer/becomeVip",
            dataType: "json",
            data: JSON.stringify({
                "id": $("#customer-m-becomeVip-id").val()
            })
        };
        showResult(settings);
    },
    customerGoldList: function () {
        var settings = {
            type: "POST",
            url: "/management/goldRecord/customerList",
            dataType: "json",
            data: JSON.stringify({
                "customerId": $("#customer-m-GoldList-customerId").val(),
                "pageIndex": $("#customer-m-GoldList-startIndex").val(),
                "pageSize": $("#customer-m-GoldList-pageSize").val()
            })
        };
        showResult(settings);
    },
    businessGoldList: function () {
        var settings = {
            type: "POST",
            url: "/management/goldRecord/businessList",
            dataType: "json",
            data: JSON.stringify({
                "businessId": $("#business-m-GoldList-businessId").val(),
                "pageIndex": $("#business-m-GoldList-startIndex").val(),
                "pageSize": $("#business-m-GoldList-pageSize").val()
            })
        };
        showResult(settings);
    },
    customerOilDrillList: function () {
        var settings = {
            type: "POST",
            url: "/management/oilDrillRecord/customerList",
            dataType: "json",
            data: JSON.stringify({
                "customerId": $("#customer-m-oilDrillList-customerId").val(),
                "pageIndex": $("#customer-m-oilDrillList-startIndex").val(),
                "pageSize": $("#customer-m-oilDrillList-pageSize").val()
            })
        };
        showResult(settings);
    },
    businessOilDrillList: function () {
        var settings = {
            type: "POST",
            url: "/management/oilDrillRecord/businessList",
            dataType: "json",
            data: JSON.stringify({
                "businessId": $("#business-m-oilDrillList-businessId").val(),
                "pageIndex": $("#business-m-oilDrillList-startIndex").val(),
                "pageSize": $("#business-m-oilDrillList-pageSize").val()
            })
        };
        showResult(settings);
    },
    customerCoinList: function () {
        var settings = {
            type: "POST",
            url: "/management/coinRecord/list",
            dataType: "json",
            data: JSON.stringify({
                "customerId": $("#customer-m-coinList-customerId").val(),
                "pageIndex": $("#customer-m-coinList-startIndex").val(),
                "pageSize": $("#customer-m-coinList-pageSize").val()
            })
        };
        showResult(settings);
    },
};


var systemApiParamsModule= {
    list: function () {
        var settings = {
            type: "GET",
            url: "/api/systemParams/list",
            dataType: "json"
        };
        showResult(settings);
    },
};