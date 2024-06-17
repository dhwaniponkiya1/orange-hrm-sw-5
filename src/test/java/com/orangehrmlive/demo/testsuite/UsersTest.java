package com.orangehrmlive.demo.testsuite;

import com.orangehrmlive.demo.pages.*;
import com.orangehrmlive.demo.testbase.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import resources.testdata.TestData;

public class UsersTest extends BaseTest {

    LoginPage loginPage;
    HomePage homePage;
    AdminPage adminPage;
    AddUserPage addUserPage;
    ViewSystemUsersPage viewSystemUsersPage;

    @BeforeMethod(alwaysRun = true)
    public void inIt() {
        loginPage = new LoginPage();
        homePage = new HomePage();
        adminPage = new AdminPage();
        addUserPage = new AddUserPage();
        viewSystemUsersPage = new ViewSystemUsersPage();

    }

    @Test(groups = {"sanity", "smoke", "regression"})
    public void adminShouldAddUserSuccessFully() throws InterruptedException {
        //login account
        loginPage.loginToApp("Admin","admin123");

        Thread.sleep(2000);
        homePage.clickOnAdminTab();         //click on admin tab
        Assert.assertEquals(adminPage.getSystemUsersText(), "System Users");        //verify text
        adminPage.clickOnAddButton();       //click on add
        Assert.assertEquals(addUserPage.getAddUserText(), "Add User");     //verify add user
        addUserPage.clickOnRoleDropdown();              //expand dropdown
        addUserPage.selectAdminRoleFromDropdown();      //select admin user role
//        addUserPage.enterEmpName("Ranga  Akunuri");          //enter emp name
        addUserPage.enterUserName("ranganku");         //enter user name
        addUserPage.selectStatusDropdown();                       // click on dropdown select status
        addUserPage.selectDisabledStatusFromDropdown();         //select disable
        addUserPage.enterPassword("prime1234");         //enter pwd
        addUserPage.enterConfirmPassword("prime1234");      //enter confirm pwd
        addUserPage.clickOnSaveButton();            //click save

    }

    @Test(groups = {"smoke", "regression"})
    public void searchTheUserCreatedAndVerifyIt() throws InterruptedException {
        //login account
        loginPage.loginToApp("Admin","admin123");

        homePage.clickOnAdminTab();         //click on admin tab
        Assert.assertEquals(adminPage.getSystemUsersText(), "System Users");        //verify text
        //enter details and click on search
        viewSystemUsersPage.enterUsername("Admin");
        viewSystemUsersPage.clickOnRoleDropdown();
        viewSystemUsersPage.selectRoleFromDropdown("Admin");
        viewSystemUsersPage.selectStatusDropdown();
        viewSystemUsersPage.selectStatusFromDropdown("Enabled");
        viewSystemUsersPage.clickOnSearchButton();
//        List<String> userDetail = viewSystemUsersPage.getUserDetails();
//        Assert.assertEquals(userDetail.get(1),"Admin");
//        Assert.assertEquals(userDetail.get(2),"Admin");
//        Assert.assertEquals(userDetail.get(4),"Enabled");

    }




    @Test(groups = {"regression"})
    public void verifyThatAdminShouldDeleteTheUserSuccessFully() {
        //login account
        loginPage.loginToApp("Admin","admin123");

        homePage.clickOnAdminTab();         //click on admin tab
        Assert.assertEquals(adminPage.getSystemUsersText(), "System Users");        //verify text
        //enter details and click on search
        viewSystemUsersPage.enterUsername("ramanji");
        viewSystemUsersPage.clickOnRoleDropdown();
        viewSystemUsersPage.selectRoleFromDropdown("Admin");
        viewSystemUsersPage.selectStatusDropdown();
        viewSystemUsersPage.selectStatusFromDropdown("Enabled");
        viewSystemUsersPage.clickOnSearchButton();
//        List<String> userDetail = viewSystemUsersPage.getUserDetails();
//        Assert.assertEquals(userDetail.get(1),"Admin");
//        Assert.assertEquals(userDetail.get(2),"Admin");
//        Assert.assertEquals(userDetail.get(4),"Enabled");

        viewSystemUsersPage.clickOnSelectUserCheckbox();
        viewSystemUsersPage.clickOnDeleteUserButton();
        viewSystemUsersPage.clickOnYesDeleteButton();
        Assert.assertEquals(viewSystemUsersPage.getSuccessDeleteToasterMsg(), "Successfully Deleted");
    }

    @Test(groups = {"regression"}, dataProvider = "searchUserData", dataProviderClass = TestData.class)
    public void searchTheUserAndVerifyTheMessageRecordFound(String username, String role, String empName, String status) throws InterruptedException {
        loginPage.loginToApp("Admin","admin123");

        Thread.sleep(2000);
        homePage.clickOnAdminTab();         //click on admin tab
        Assert.assertEquals(adminPage.getSystemUsersText(), "System Users");        //verify text
        //enter details and click on search
        viewSystemUsersPage.enterUsername(username);
        //viewSystemUsersPage.enterEmpName(empName);
        viewSystemUsersPage.clickOnRoleDropdown();
        viewSystemUsersPage.selectRoleFromDropdown(role);
        viewSystemUsersPage.selectStatusDropdown();
        viewSystemUsersPage.selectStatusFromDropdown(status);
//        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Search']"))).click();

        Thread.sleep(2000);
        viewSystemUsersPage.clickOnSearchButton();

        Assert.assertEquals(viewSystemUsersPage.getRecordFoundText(), "(1) Record Found");
        Assert.assertEquals(viewSystemUsersPage.getUsernameText(), username);         //verify username
        viewSystemUsersPage.clickOnResetButton();           //reset all data
    }

}
