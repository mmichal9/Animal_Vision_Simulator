#extension GL_OES_standard_derivatives : enable
precision mediump float;

uniform vec3                iResolution;
uniform vec3                iChannelResolution;
uniform float               iGlobalTime;
uniform sampler2D           iChannel0;
varying vec2                texCoord;



// Created by mmichal



#define H 0.010
#define S ((3./2.) * H/sqrt(6.))

vec2 xy_coord(vec2 Index) {
	float i = Index.x;
	float j = Index.y;
	vec2 r;

	r.x = i * S;
	r.y = j * H + (mod(i,2.0)) * H/2.0;
	return r;
}

vec2 Index(vec2 xy_coord) {
	vec2 r;
	float x = xy_coord.x;
	float y = xy_coord.y;

	float it = float(floor(x/S));
	float yts = y - (mod(it,2.0)) * H/2.;
	float jt = float(floor((1./H) * yts));
	float xt = x - it * S;
	float yt = yts - jt * H;
	float deltaj = (yt > H/2.)? 1.0:0.0;
	float fcond = S * (2./3.) * abs(0.5 - yt/H);

	if (xt > fcond) {
		r.x = it;
		r.y = jt;
    }else {
		r.x = it - 1.0;
		r.y = jt - (mod(r.x,2.0)) + deltaj;
	}
	return r;
}

void mainImage( out vec4 fragColor, in vec2 fragCoord) {
	vec2 uv = fragCoord.xy;
	vec2 hexIx = Index(uv);
	vec2 hexXy = xy_coord(hexIx);
	vec4 fcol = texture2D(iChannel0, hexXy);
	fragColor = fcol;
}

void main() {
	mainImage(gl_FragColor, texCoord);
}
