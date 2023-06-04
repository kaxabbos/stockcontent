package com.stockcontent.controller;

import com.stockcontent.controller.main.Attributes;
import com.stockcontent.model.*;
import com.stockcontent.model.enums.adStatus;
import com.stockcontent.model.enums.Category;
import com.stockcontent.model.enums.Type;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequestMapping("/ads")
public class AdsCont extends Attributes {

    @GetMapping("/{id}")
    public String Ad(Model model, @PathVariable Long id) {
        AddAttributesAd(model, id);
        return "ad";
    }

    @PostMapping("/{id}/comment/add")
    public String CommentAdd(@PathVariable Long id, @RequestParam String text) {
        System.out.println(1);
        Ads ad = adsRepo.getReferenceById(id);
        System.out.println(2);
        ad.addComment(new Comments(getUser().getUsername(), DateNow(), text));
        System.out.println(3);
        adsRepo.save(ad);
        System.out.println(4);
        return "redirect:/ads/{id}";
    }

    @GetMapping("/add")
    public String AdAdd(Model model) {
        AddAttributesAdAdd(model);
        return "adAdd";
    }

    @GetMapping("/conf/{adId}")
    public String AdConf(Model model, @PathVariable Long adId) {
        Ads ad = adsRepo.getReferenceById(adId);
        ad.setStatus(adStatus.ACTIVE);
        adsRepo.save(ad);
        AddAttributesAdAdd(model);
        return "redirect:/ads/{adId}";
    }

    @GetMapping("/refused/{adId}")
    public String AdRefused(Model model, @PathVariable Long adId) {
        Ads ad = adsRepo.getReferenceById(adId);
        ad.setStatus(adStatus.REFUSED);
        adsRepo.save(ad);
        AddAttributesAdAdd(model);
        return "redirect:/ads/{adId}";
    }

    @GetMapping("/waiting/{adId}")
    public String AdWaiting(Model model, @PathVariable Long adId) {
        Ads ad = adsRepo.getReferenceById(adId);
        ad.setStatus(adStatus.WAITING);
        adsRepo.save(ad);
        AddAttributesAdAdd(model);
        return "redirect:/ads/{adId}";
    }

    @GetMapping("/sold/{adId}")
    public String AdSold(Model model, @PathVariable Long adId) {
        Ads ad = adsRepo.getReferenceById(adId);
        ad.setStatus(adStatus.SOLD);
        requestsRepo.deleteAllInBatch(ad.getRequests());
        adsRepo.save(ad);
        AddAttributesAdAdd(model);
        return "redirect:/ads/{adId}";
    }

    @GetMapping("/apps")
    public String AdApps(Model model) {
        AddAttributesAdApps(model);
        return "apps";
    }

    @GetMapping("/my")
    public String AdMy(Model model) {
        AddAttributesAdMy(model);
        return "my";
    }

    @GetMapping("/edit/{id}")
    public String AdEdit(Model model, @PathVariable Long id) {
        AddAttributesAdEdit(model, id);
        return "adEdit";
    }

    @GetMapping("/delete/{id}")
    public String AdDelete(@PathVariable Long id) {
        adsRepo.deleteById(id);
        return "redirect:/";
    }

    @PostMapping("/requests/add/{id}")
    public String AdRequestAdd(@PathVariable Long id, @RequestParam String message, @RequestParam String tel) {
        requestsRepo.save(new Requests(adsRepo.getReferenceById(id), getUser(), message, tel));
        return "redirect:/ads/{id}";
    }

    @GetMapping("/requests/{id}")
    public String AdRequests(Model model, @PathVariable Long id) {
        AddAttributes(model);
        model.addAttribute("requests", adsRepo.getReferenceById(id).getRequests());
        return "requests";
    }

    @PostMapping("/add")
    public String AdAddNew(Model model, @RequestParam String name, @RequestParam MultipartFile[] photos, @RequestParam int price, @RequestParam Category category, @RequestParam String description, @RequestParam Type type, @RequestParam String tel) {
        if (photos != null && !Objects.requireNonNull(photos[0].getOriginalFilename()).isEmpty()) {
            try {
                String[] result_photos;
                String result_screenshot;
                String uuidFile = UUID.randomUUID().toString();
                result_photos = new String[photos.length];
                for (int i = 0; i < result_photos.length; i++) {
                    result_screenshot = "files/" + uuidFile + "_" + photos[i].getOriginalFilename();
                    photos[i].transferTo(new File(uploadImg + "/" + result_screenshot));
                    result_photos[i] = result_screenshot;
                }
                Ads ad = new Ads(name, price, tel, result_photos);
                ad.setDescription(new AdDescription(category, type, description));
                Users user = getUser();
                user.addAd(ad);
                usersRepo.save(user);
            } catch (Exception e) {
                AddAttributesAdAdd(model);
                model.addAttribute("message", "Ошибка, некорректный данные!");
                return "adAdd";
            }
        } else {
            AddAttributesAdAdd(model);
            model.addAttribute("message", "Ошибка, некорректный данные!");
            return "adAdd";
        }
        return "redirect:/ads/add";
    }

    @PostMapping("/edit/{id}")
    public String AdEditOld(Model model, @RequestParam String name, @RequestParam MultipartFile[] photos, @RequestParam int price, @RequestParam Category category, @RequestParam String description, @RequestParam Type type, @RequestParam String tel, @PathVariable Long id) {
        Ads ad = adsRepo.getReferenceById(id);
        String[] result_photos;
        if (photos != null && !Objects.requireNonNull(photos[0].getOriginalFilename()).isEmpty()) {
            try {
                String result_photo;
                String uuidFile = UUID.randomUUID().toString();
                result_photos = new String[photos.length];
                for (int i = 0; i < result_photos.length; i++) {
                    result_photo = "files/" + uuidFile + "_" + photos[i].getOriginalFilename();
                    photos[i].transferTo(new File(uploadImg + "/" + result_photo));
                    result_photos[i] = result_photo;
                }
                ad.setPhotos(result_photos);
            } catch (Exception e) {
                AddAttributesAdAdd(model);
                model.addAttribute("message", "Ошибка, некорректный данные!");
                return "adEdit";
            }
        }

        ad.setName(name);
        ad.setPrice(price);
        ad.setTel(tel);

        AdDescription adDescription = ad.getDescription();
        adDescription.setDescription(description);
        adDescription.setType(type);
        adDescription.setCategory(category);

        adsRepo.save(ad);

        return "redirect:/";
    }
}
