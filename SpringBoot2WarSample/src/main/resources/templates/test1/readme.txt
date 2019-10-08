타임리프의 기본 기능만 사용하는 방법으로

레이아웃별로 defaultLayout.html, popupLayout.html 등으로 생성하고

controller에서 아래처럼 던지는 방법

model.addAttribute("template", "test1/index");
		
return "test1/defaultLayout";