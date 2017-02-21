window.onload=function()
{
	var oDiv1=document.getElementById('div1');
	var oDiv2=document.getElementById('div2');
	var oDiv3=document.getElementById('div3');
	var oDiv4=document.getElementById('div4');
	var oDiv5=document.getElementById('div5');
	var oDiv6=document.getElementById('div6');

	oDiv1.onclick=function (ev)
	{
		oDiv2.style.display='block';
		oDiv5.style.display='none';
	};
	oDiv3.onclick=function (ev)
	{
		oDiv2.style.display='none';
	};
	oDiv4.onclick=function (ev)
	{
		oDiv2.style.display='none';
		oDiv5.style.display='block';
	};
	oDiv6.onclick=function (ev)
	{
		oDiv5.style.display='none';
	};
};