var app = angular.module('recipes', []).config(function ($httpProvider) {
    csrftoken = $("meta[name='_csrf']").attr("content");
    csrfheader = $("meta[name='_csrf_header']").attr("content");
    $httpProvider.defaults.headers.common["X-CSRF-TOKEN"] = csrftoken;
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(csrfheader, csrftoken);
    });
});
app.controller("RecipesController", function ($scope, $http) {

    $scope.successGetRecipesCallback = function (response) {                
        $scope.recipesList = response.data;
        $scope.errormessage="";
    };

    $scope.errorGetRecipesCallback = function (error) {
        console.log(error);
        $scope.errormessage="Не могу получить список рецептов";
    };

    $scope.getRecipes = function () {
        $http.get('/public/rest/recipes').then($scope.successGetRecipesCallback, $scope.errorGetRecipesCallback);
    };

    $scope.successDeleteRecipeCallback = function (response) {
        for (var i = 0; i < $scope.recipesList.length; i++) {
            var j = $scope.recipesList[i];
            if (j.id === $scope.deletedId) {
                $scope.recipesList.splice(i, 1);
                break;
            }
        }
        $scope.errormessage="";
    };

    $scope.errorDeleteRecipeCallback = function (error) {
        console.log(error);
        $scope.errormessage="Невозможно удалить рецепт.";
    };

    $scope.deleteRecipe = function (id) {
        $scope.deletedId = id;
        $http.delete('/public/rest/recipes/' + id).then($scope.successDeleteRecipeCallback, $scope.errorDeleteRecipeCallback);
    };

    $scope.successGetRecipeCallback = function (response) {
        $scope.recipesList.splice(0, 0, response.data);
        $scope.errormessage="";
    };

    $scope.errorGetRecipeCallback = function (error) {
        console.log(error);
        $scope.errormessage="Невозможно найти информацию о рецепте";
    };

    $scope.successAddRecipeCallback = function (response) {
        $http.get('/public/rest/recipes/' + $scope.inputname).then($scope.successGetRecipeCallback, $scope.errorGetRecipeCallback);
        $scope.errormessage="";
    };

    $scope.errorAddRecipeCallback = function (error) {
        console.log(error);
        $scope.errormessage="Невозможно добавить новый рецепт";
    };

    $scope.addRecipe = function () {
        $http.post('/public/rest/recipes/' + $scope.inputname + "/" + $scope.inputdescription+"/"+$scope.inputtext).then($scope.successAddRecipeCallback, $scope.errorAddRecipeCallback);
    };

    $scope.successGetRecipeCallback1 = function (response) {
        //var dataInput =document.getElementById("searchdate");
        if (JSON.stringify(response.data) === ""){
            $scope.searchdescription="";
            $scope.searchtext='';
        }
        else{
            $scope.searchdescription=response.data.description;
            $scope.searchtext=response.data.text;
        }
    };

    $scope.errorGetRecipeCallback1 = function (error) {
        console.log(error);
        $scope.errormessage="Невозможно найти информацию о рецепте";
    };
    $scope.GetRecipe = function () {
        $http.get('/public/rest/recipes/' + $scope.searchname).then($scope.successGetRecipeCallback1, $scope.errorGetRecipeCallback1);
    };
});