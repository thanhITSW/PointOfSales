package com.tt.controller;

import com.tt.entity.Account;
import com.tt.entity.Product;
import com.tt.service.AccountService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/accounts")
public class AccountController {
    AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @GetMapping("")
    public String index(Model model){
        List<Account> accountList = service.getAll();
        model.addAttribute("accounts", accountList);
        return "ManageAccount";
    }

    @GetMapping("/add")
    public String getAdd(){
        return "redirect:/accounts";
    }

    @PostMapping ("/add")
    public String postAdd(Account a, Model model, HttpServletRequest req) throws ServletException, IOException {
        if(a.getFullName().equals("") || a.getFullName() == null){
            model.addAttribute("errorMessageAlert", "Full name can not be empty");
            return "ManageAccount";
        }
        else if(a.getEmail().equals("") || a.getEmail() == null){
            model.addAttribute("errorMessageAlert", "Email can not be empty");
            return "ManageAccount";
        }
        else if(a.getPhoneNumber().equals("") || a.getPhoneNumber() == null){
            model.addAttribute("errorMessageAlert", "Phone number can not be empty");
            return "ManageAccount";
        }
        else if(a.getRole().equals("") || a.getRole() == null){
            model.addAttribute("errorMessageAlert", "Role can not be empty");
            return "ManageAccount";
        }
        else {
            Part part = req.getPart("image");
            if (part.getSize() > 0){
                File file = new File("");
                String currentDirectory = file.getAbsolutePath();
                String uploadPath = currentDirectory + "\\src\\main\\resources\\static\\Image\\avatars";
                String destination = uploadPath + File.separator + part.getSubmittedFileName();
                String url = part.getSubmittedFileName();
                part.write(destination);
                a.setUrlAvatar(url);
            }
            else {
                a.setUrlAvatar("default.png");
            }
            String username = a.getEmail().split("@")[0];
            String password = username;
            a.setUsername(username);
            a.setPassword(password);
            a.setActive(false);
            a.setStatus("unlock");
            model.addAttribute("successMessageAlert", "Add accounts sucessfully!");
            service.add(a);
            return "ManageAccount";
        }
    }


    @PostMapping("/edit")
    public String edit(@ModelAttribute("accounts") Account account, Model model, HttpServletRequest req) throws IOException, ServletException {

        try {
            int accountId = Integer.parseInt(req.getParameter("id"));

            Account existingAccount = service.findById(accountId);
            if (existingAccount == null) {
                model.addAttribute("errorMessageAlert", "Account not found.");
                return "ManageAccount";
            }

            existingAccount.setFullName(account.getFullName());
            existingAccount.setEmail(account.getEmail());
            existingAccount.setPhoneNumber(account.getPhoneNumber());

            Part part = req.getPart("image");
            if (part.getSize() > 0) {
                File file = new File("");
                String currentDirectory = file.getAbsolutePath();
                String uploadPath = currentDirectory + "\\src\\main\\resources\\static\\Image\\avatars";
                String destination = uploadPath + File.separator + part.getSubmittedFileName();
                String url = part.getSubmittedFileName();
                part.write(destination);

                //xoa image truoc do
                String imagePath = uploadPath + File.separator + existingAccount.getUrlAvatar();
                File imageFile = new File(imagePath);
                if (imageFile.exists()) {
                    imageFile.delete();
                }

                existingAccount.setUrlAvatar(url);
            }

            service.updateById(accountId,existingAccount);

            model.addAttribute("successMessageAlert", "Account updated successfully!");
            return "ManageAccount";
        } catch (Exception e) {
            model.addAttribute("errorMessageAlert", "Error updating account: " + e.getMessage());
            return "ManageAccount";
        }
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") int accountId, Model model, HttpServletRequest req){
        try {
            Account existingAccount = service.findById(accountId);
            if (existingAccount == null) {
                model.addAttribute("errorMessageAlert", "Account not found.");
                return "ManageAccount";
            }

            File file = new File("");
            String currentDirectory = file.getAbsolutePath();
            String uploadPath = currentDirectory + "\\src\\main\\resources\\static\\Image\\avatars";
            String imagePath = uploadPath + File.separator + existingAccount.getUrlAvatar();

            if (imagePath != null && !imagePath.isEmpty()) {
                File imageFile = new File(imagePath);
                if (imageFile.exists()) {
                    imageFile.delete();
                }
            }

            service.deleteById(accountId);

            model.addAttribute("successMessageAlert", "Account deleted successfully!");
            return "ManageAccount";
        } catch (Exception e) {
            model.addAttribute("errorMessageAlert", "Error deleting product: " + e.getMessage());
            return "ManageAccount";
        }
    }

    @PostMapping("/delete-many")
    public String deleteMany(@RequestParam(value = "selectedIds", required = false) List<Integer> selectedIds, Model model){
        try {
            if (selectedIds != null && !selectedIds.isEmpty()) {
                for (Integer accountId : selectedIds) {
                    Account existingAccount = service.findById(accountId);
                    if (existingAccount != null) {
                        // Xóa file ảnh
                        File file = new File("");
                        String currentDirectory = file.getAbsolutePath();
                        String uploadPath = currentDirectory + "\\src\\main\\resources\\static\\Image\\avatars";
                        String imagePath = uploadPath + File.separator + existingAccount.getUrlAvatar();
                        if (imagePath != null && !imagePath.isEmpty()) {
                            File imageFile = new File(imagePath);
                            if (imageFile.exists()) {
                                imageFile.delete();
                            }
                        }

                        // Xóa tài khoản
                        service.deleteById(accountId);
                    }
                }

                model.addAttribute("successMessageAlert", "Selected accounts deleted successfully!");
            } else {
                model.addAttribute("errorMessageAlert", "No accounts selected for deletion.");
            }

            return "ManageAccount";
        } catch (Exception e) {
            model.addAttribute("errorMessageAlert", "Error deleting accounts: " + e.getMessage());
            return "ManageAccount";
        }
    }

    @PostMapping("/lock-unlock")
    public String lockUnlock(@RequestParam(value = "selectedIds", required = false) List<Integer> selectedIds,
                             @RequestParam(value = "status") String status, Model model){
        try {
            if (selectedIds != null && !selectedIds.isEmpty()) {
                for (Integer accountId : selectedIds) {
                    Account existingAccount = service.findById(accountId);
                    if (existingAccount != null) {
                        // Thay đổi trạng thái
                        existingAccount.setStatus(status);

                        // Cập nhật tài khoản
                        service.add(existingAccount);
                    }
                }

                model.addAttribute("successMessageAlert", "Selected accounts updated successfully!");
            } else {
                model.addAttribute("errorMessageAlert", "No accounts selected for status update.");
            }

            return "ManageAccount";
        } catch (Exception e) {
            model.addAttribute("errorMessageAlert", "Error updating account status: " + e.getMessage());
            return "ManageAccount";
        }
    }
}
