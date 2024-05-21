import { inject } from "@angular/core";
import { Router } from "@angular/router";
 
export const loginGuard = ()=>{
 
 
    const router=inject(Router);
 
  if (typeof window !== 'undefined' && window.sessionStorage) {
    if (sessionStorage.getItem('token')) {
      return true;
    } else {
      router.navigate(['/login']);
      return false;
    }
  } else {
    router.navigate(['/login']);
    return false;
  }
}